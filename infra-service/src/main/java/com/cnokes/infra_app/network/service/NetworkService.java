package com.cnokes.infra_app.network.service;

import java.util.List;
import java.util.Optional;

import com.cnokes.infra_app.network.repo.MongoNetworkPlanRepo;
import com.cnokes.infra_app.network.repo.NetworkPlan;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetworkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkService.class);

    @Autowired
    MongoNetworkPlanRepo repo;

    public NetworkPlan insert(NetworkPlan networkPlan) {
        return insert(new NetworkPlanBO(networkPlan));
    }

    public NetworkPlan insert(NetworkPlanBO networkPlanBO) {
        return repo.insert(networkPlanBO.getNetworkPlan());
    }

    public Optional<NetworkPlan> findByName(String name) {
        return repo.findByName(name);
    }

    public Optional<NetworkPlan> findById(String id) {
        LOGGER.info("id=" + id);
        return repo.findById(new ObjectId(id));
    }

    public List<NetworkPlan> findAll() {
        return repo.findAll();
    }

    public NetworkPlan save(NetworkPlan networkPlan) {
        return save(new NetworkPlanBO(networkPlan));
    }

    public NetworkPlan save(NetworkPlanBO networkPlanBO) {
        return repo.save(networkPlanBO.getNetworkPlan());
    }
}
