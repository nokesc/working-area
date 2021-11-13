package com.cnokes.infra_service.web;

import java.util.List;

import com.cnokes.infra_service.network.PrefixLength;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/prefix-lengths")
@Tag(name = "prefix-lengths")
public class PrefixLengthsController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PrefixLength> getAll() {
		return PrefixLength.ALL;
	}
}