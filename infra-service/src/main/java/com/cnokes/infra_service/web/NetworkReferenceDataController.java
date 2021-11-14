package com.cnokes.infra_service.web;

import java.util.List;

import com.cnokes.infra_service.network.IPV4Utils;
import com.cnokes.infra_service.network.PrefixLength;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController

@RequestMapping("/ipv4-network-reference-data")
@Tag(name = "network-reference-data")
public class NetworkReferenceDataController {

	@RequestMapping("/prefix-lengths")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PrefixLength> getAll() {
		return PrefixLength.ALL;
	}

	@RequestMapping("/private-cidr-blocks")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getPrivate() {
		return IPV4Utils.MaxPrivateNetworkCIDRBlocks.ALL;
	}
}