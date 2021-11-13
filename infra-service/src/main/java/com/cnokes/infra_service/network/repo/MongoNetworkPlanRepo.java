package com.cnokes.infra_service.network.repo;

import java.util.Optional;

import com.cnokes.infra_service.network.repo.model.NetworkPlan;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoNetworkPlanRepo extends MongoRepository<NetworkPlan, ObjectId> {
	public Optional<NetworkPlan> findByName(String name);
}