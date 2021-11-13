package com.cnokes.infra_service.network.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.cnokes.infra_service.network.NetworkPlanBO;
import com.cnokes.infra_service.network.repo.model.Network;
import com.cnokes.infra_service.network.repo.model.NetworkPlan;

import org.junit.jupiter.api.Test;

public class TestDataSetupTest {

    @Test
    public void createTestData_1() {
        List<NetworkPlanBO> testData = TestDataSetup.createTestData(2);
        assertThat(2).isEqualTo(testData.size());
        {
            NetworkPlanBO networkPlanBO = testData.get(0);
            NetworkPlan networkPlan = networkPlanBO.getNetworkPlan();
            Network network = networkPlan.getNetwork();
            List<Network> subnets = networkPlan.getSubnets();
            assertThat(4).isEqualTo(subnets.size());
            assertThat("10.16.0.0/16").isEqualTo(network.getCidrBlock());
            assertThat("10.16.0.0/24").isEqualTo(subnets.get(0).getCidrBlock());
            assertThat("10.16.1.0/24").isEqualTo(subnets.get(1).getCidrBlock());
            assertThat("10.16.2.0/24").isEqualTo(subnets.get(2).getCidrBlock());
            assertThat("10.16.3.0/24").isEqualTo(subnets.get(3).getCidrBlock());
        }
        {
            NetworkPlanBO networkPlanBO = testData.get(1);
            NetworkPlan networkPlan = networkPlanBO.getNetworkPlan();
            Network network = networkPlan.getNetwork();
            List<Network> subnets = networkPlan.getSubnets();
            assertThat(4).isEqualTo(subnets.size());
            assertThat("10.17.0.0/16").isEqualTo(network.getCidrBlock());
            assertThat("10.17.0.0/24").isEqualTo(subnets.get(0).getCidrBlock());
            assertThat("10.17.1.0/24").isEqualTo(subnets.get(1).getCidrBlock());
            assertThat("10.17.2.0/24").isEqualTo(subnets.get(2).getCidrBlock());
            assertThat("10.17.3.0/24").isEqualTo(subnets.get(3).getCidrBlock());
        }
    }

}
