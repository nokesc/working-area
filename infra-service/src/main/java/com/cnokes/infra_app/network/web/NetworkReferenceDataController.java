package com.cnokes.infra_app.network.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.cnokes.infra_app.network.service.IPV4Utils;
import com.cnokes.infra_app.network.service.PrefixLength;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController

@RequestMapping("/ipv4-network-reference-data")
@Tag(name = "network-reference-data")
public class NetworkReferenceDataController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkReferenceDataController.class);

	@Getter
	@AllArgsConstructor
	public static final class PrivateCidrBlock {
		private final String cidrBlock;
		private final long hosts;
	}

	private final List<PrivateCidrBlock> privateCidrBlocks;

	public NetworkReferenceDataController() {
		privateCidrBlocks = Collections
				.unmodifiableList(IPV4Utils.MaxPrivateNetworkCIDRBlocks.ALL.stream()
						.map(cidrBlock -> new PrivateCidrBlock(cidrBlock,
								IPV4Utils.toCIDRBlock(cidrBlock).getPrefixLength().getHosts()))
						.collect(Collectors.toList()));
	}

	@GetMapping(path = "/private-cidr-blocks", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PrivateCidrBlock> getPrivatePrivateCidrBlocks(Authentication authentication) {
		LOGGER.info("authentication: " + authentication.getName());
		return privateCidrBlocks;
	}

	@GetMapping(path = "/prefix-lengths", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PrefixLength> getPrefixLengths() {
		return PrefixLength.ALL;
	}
}