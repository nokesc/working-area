package com.cnokes.infra_app.network.service;

import static org.nebula.utils.json.JacksonMapper.JSON_MAPPER;

import java.util.Arrays;

import com.cnokes.framework.model.IndexedList;
import com.cnokes.framework.web.exception.FieldError;
import com.cnokes.framework.web.exception.HttpBadRequestException;
import com.cnokes.infra_app.network.repo.Network;
import com.cnokes.infra_app.network.repo.NetworkPlan;
import com.cnokes.infra_app.network.service.IPV4Utils.CIDRBlock;

public class NetworkPlanBO {

	private final NetworkPlan networkPlan;

	private final CIDRBlock cidrBlock;

	private final IndexedList<Network, CIDRBlock, Long> subnetsIndex;

	public NetworkPlanBO(NetworkPlan in) {
		this.networkPlan = JSON_MAPPER.clone(in);
		Network network = this.networkPlan.getNetwork();
		if (network == null) {
			throw new HttpBadRequestException(new FieldError("network", "REQ"));
		} else {
			cidrBlock = IPV4Utils.tryCIDRBlock(this.networkPlan.getNetwork().getCidrBlock());
			if (cidrBlock == null) {
				throw new HttpBadRequestException(this.networkPlan.getNetwork().getCidrBlock(),
						Arrays.asList(new FieldError("network.cidrBlock", "IL")));
			}
		}

		subnetsIndex = new IndexedList<Network, CIDRBlock, Long>(
				subnet -> IPV4Utils.toCIDRBlock(subnet.getCidrBlock()), CIDRBlock::getAddressAsLong,
				this.networkPlan.getSubnets()) {
			@Override
			protected void preInsert_validateExact(TargetAndField toInsert, int insertLocation,
					TargetAndField existing) {
				throw new HttpBadRequestException(
						new FieldError("cidrBlock", "NPBO_AN1: Block not available: " + toInsert.getField()));
			}

			@Override
			protected void preInsert_validatePrevious(TargetAndField toInsert, int insertLocation,
					TargetAndField previous) {
				CIDRBlock toInsertCidrBlock = toInsert.getField();
				CIDRBlock previousCidrBlock = previous.getField();
				if (previousCidrBlock.getLastAddressAsLong() >= toInsertCidrBlock.getAddressAsLong()) {
					throw new HttpBadRequestException(new FieldError("cidrBlock",
							String.format("NPBO_AN2: Insertion block (%s) start is before previous block (%s) end",
									toInsertCidrBlock, previousCidrBlock)));
				}
			}

			@Override
			protected void preInsert_validateNext(TargetAndField toInsert, int insertLocation, TargetAndField next) {
				CIDRBlock toInsertCidrBlock = toInsert.getField();
				CIDRBlock nextCidrBlock = next.getField();
				if (toInsertCidrBlock.getLastAddressAsLong() >= nextCidrBlock.getAddressAsLong()) {
					throw new HttpBadRequestException(new FieldError("cidrBlock",
							String.format("NPBO_AN3: Insertion block (%s) end is after next block (%s) start",
									toInsertCidrBlock, nextCidrBlock)));
				}
			}

			@Override
			protected void preInsert_ready(IndexedList<Network, CIDRBlock, Long>.TargetAndField toInsert,
					int insertLocation) {
				CIDRBlock toInsertCidrBlock = toInsert.getField();
				if (toInsertCidrBlock.getAddressAsLong() < cidrBlock.getAddressAsLong()
						|| toInsertCidrBlock.getLastAddressAsLong() > cidrBlock.getLastAddressAsLong()) {
					throw new HttpBadRequestException(new FieldError("cidrBlock",
							String.format("NPBO_AN4: Subnet block (%s) to add is not contained in parent (%s)",
									toInsertCidrBlock, cidrBlock)));

				}
			}
		};
	}

	public NetworkPlan getNetworkPlan() {
		return networkPlan.setSubnets(subnetsIndex.getItems());
	}

	public void addSubnet(Network subnet) {
		subnetsIndex.add(subnet);
	}

	public CIDRBlock findOpenBlock(PrefixLength prefixLength) {
		CIDRBlock search = cidrBlock.toPrefixLength(prefixLength);
		while (search.getLastAddressAsLong() < cidrBlock.getLastAddressAsLong()) {
			if (subnetsIndex.validate(new Network().setCidrBlock(search.getValue()))) {
				return search;
			}
			search = search.nextBlock();
		}
		return null;
	}
}
