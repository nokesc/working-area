package com.cnokes.infra_service.network.repo.model;

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
public class Network {
	String owner;
	String name;
	String cidrBlock;
}