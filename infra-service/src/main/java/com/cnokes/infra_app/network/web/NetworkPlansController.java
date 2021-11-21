package com.cnokes.infra_app.network.web;

import java.util.List;
import java.util.Optional;

import com.cnokes.framework.web.exception.BaseErrorResponse;
import com.cnokes.framework.web.exception.HttpNotFoundException;
import com.cnokes.infra_app.network.repo.NetworkPlan;
import com.cnokes.infra_app.network.service.NetworkService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/network-plans")
@Tag(name = "network-plans")
public class NetworkPlansController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkPlansController.class);

	@Autowired
	NetworkService networkService;

	@ApiResponses(@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))))
	@GetMapping(path = "/get-by-name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NetworkPlan getByName(@Parameter(example = "name_1") @PathVariable(value = "name") String name) {
		LOGGER.info("name=" + name);
		Optional<NetworkPlan> entity = networkService.findByName(name);
		if (entity.isPresent()) {
			return entity.get();
		}
		throw new HttpNotFoundException();
	}

	@ApiResponses(@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = BaseErrorResponse.class))))
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public NetworkPlan get(@Parameter(example = "6186c224c39e9e7f645992d6") @PathVariable(value = "id") String id) {
		LOGGER.info("id=" + id);
		Optional<NetworkPlan> entity = networkService.findById(id);
		if (entity.isPresent()) {
			return entity.get();
		}
		throw new HttpNotFoundException();
	}

	// @ApiResponses(@ApiResponse(responseCode = "404", content = @Content(schema =
	// @Schema(implementation = BaseErrorResponse.class))))
	// @GetMapping(path = "findByNetworksId/{id}", produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// public NetworkPlan getByNetworksId(
	// @Parameter(example = "6174322fbf00a07314323d13") @PathVariable(value = "id")
	// String id) {
	// LOGGER.info("id=" + id);
	// List<NetworkPlan> entity = mongoContractRepo.findByNetworksId(new
	// ObjectId(id));
	// if (entity.isEmpty()) {
	// throw new HttpNotFoundException();
	// }
	// return entity.get(0);
	// }

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NetworkPlan> getAll() {
		return networkService.findAll();
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void put(@RequestBody NetworkPlan networkPlan) {
		NetworkPlan save = networkService.save(networkPlan);
		LOGGER.info("save succeeded: " + save);
	}
}