package com.cnokes.infra_service.network.repo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class NetworkPlan {
	String id;
	String name;
	String description;
	Network network;
	List<Network> subnets;
}