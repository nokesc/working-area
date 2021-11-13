package com.cnokes.infra_service.network;

import java.util.Optional;

import com.cnokes.infra_service.network.repo.MongoNetworkPlanRepo;
import com.cnokes.infra_service.network.repo.model.NetworkPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NetworkService {
    @Autowired
    MongoNetworkPlanRepo repo;

    public NetworkPlan insert(NetworkPlan networkPlan) {
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        return insert(networkPlanBO);
    }

    public NetworkPlan insert(NetworkPlanBO networkPlanBO) {
        return repo.insert(networkPlanBO.getNetworkPlan());
    }

    public Optional<NetworkPlan> findByName(String name) {
        return repo.findByName(name);
    }
}
