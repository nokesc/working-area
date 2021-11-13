package com.cnokes.infra_service.network.repo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.cnokes.infra_service.network.IPV4Utils.CIDRBlock;
import com.cnokes.infra_service.network.NetworkPlanBO;
import com.cnokes.infra_service.network.NetworkService;
import com.cnokes.infra_service.network.PrefixLength;
import com.cnokes.infra_service.network.repo.model.Network;
import com.cnokes.infra_service.network.repo.model.NetworkPlan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties("test-data-setup")
public class TestDataSetup {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestDataSetup.class);

	static List<NetworkPlanBO> createTestData(int number) {
		List<NetworkPlanBO> result = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			String name = "np_" + i;
			Network network = new Network().setName("network_" + i).setCidrBlock(String.format("10.%s.0.0/16", i + 16));
			NetworkPlan networkPlan = new NetworkPlan().setName(name).setNetwork(network);
			NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
			for (int j = 0; j < 4; j++) {
				PrefixLength prefixLength = PrefixLength._24;
				CIDRBlock openBlock = networkPlanBO.findOpenBlock(prefixLength);
				if(openBlock == null) {
					throw new IllegalStateException("Unable to find openBlock for " + prefixLength.getValue());
				}
				networkPlanBO.addSubnet(new Network().setName("subnet_" + j)
						.setCidrBlock(openBlock.getValue()));
			}
			result.add(networkPlanBO);
		}
		return result;
	}

	boolean enable = false;
	int number = 3;

	@ToString.Exclude
	@Autowired
	NetworkService networkService;

	@PostConstruct
	public void setup() {
		LOGGER.info("setup: " + this);
		if (enable) {
			createTestData(number).forEach(networkPlanBO -> {
				if (!networkService.findByName(networkPlanBO.getNetworkPlan().getName()).isPresent()) {
					networkService.insert(networkPlanBO);
					LOGGER.info("Added " + networkPlanBO);
				}
			});
		}
	}
}