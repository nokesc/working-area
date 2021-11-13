package com.cnokes.infra_service.network;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import java.util.List;

import com.cnokes.framework.web.exception.FieldError;
import com.cnokes.framework.web.exception.HttpBadRequestException;
import com.cnokes.infra_service.network.IPV4Utils.CIDRBlock;
import com.cnokes.infra_service.network.repo.model.Network;
import com.cnokes.infra_service.network.repo.model.NetworkPlan;

import org.junit.jupiter.api.Test;

public class NetworkPlanBOTest {

    public NetworkPlanBOTest() {
    }

    @Test
    public void addSubnet_valid() {
        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/16"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/24"));
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.1.0/24"));
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.2.0/24"));
    }

    @Test
    public void findOpenBlock() {
        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/16"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/24"));
        
        CIDRBlock openBlock = networkPlanBO.findOpenBlock(PrefixLength._24);
        assertThat(openBlock.getValue()).isEqualTo("192.168.1.0/24");

        networkPlanBO.addSubnet(new Network().setCidrBlock(openBlock.getValue()));
        openBlock = networkPlanBO.findOpenBlock(PrefixLength._24);
        assertThat(openBlock.getValue()).isEqualTo("192.168.2.0/24");
    }

    @Test
    public void addSubnet_notInParentRange() {
        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/25"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        HttpBadRequestException hbre = catchThrowableOfType(() -> {
            networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/24"));
        }, HttpBadRequestException.class);
        assertThat(assertSingleCode(hbre)).contains("NPBO_AN4");
    }

    @Test
    public void addSubnet_blockNotAvailable() {
        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/16"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/23"));
        HttpBadRequestException hbre = catchThrowableOfType(() -> {
            networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/24"));
        }, HttpBadRequestException.class);
        assertThat(assertSingleCode(hbre)).contains("NPBO_AN1");
    }

    private String assertSingleCode(HttpBadRequestException hbre) {
        List<FieldError> fieldErrors = hbre.getFieldErrors();
        assertThat(fieldErrors).hasSize(1);
        FieldError fieldError = fieldErrors.get(0);
        return fieldError.getCode();
    }

    @Test
    public void addSubnet_overlapsPreviousEnd() {
        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/16"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/23"));
        HttpBadRequestException hbre = catchThrowableOfType(() -> {
            networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.1.0/24"));
        }, HttpBadRequestException.class);
        assertThat(assertSingleCode(hbre)).contains("NPBO_AN2");
    }

    @Test
    public void addSubnet_overlapsNextStart() {

        NetworkPlan networkPlan = new NetworkPlan();
        networkPlan.setNetwork(new Network().setCidrBlock("192.168.0.0/16"));
        NetworkPlanBO networkPlanBO = new NetworkPlanBO(networkPlan);
        networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.1.0/24"));
        HttpBadRequestException hbre = catchThrowableOfType(() -> {
            networkPlanBO.addSubnet(new Network().setCidrBlock("192.168.0.0/22"));
        }, HttpBadRequestException.class);
        assertThat(assertSingleCode(hbre)).contains("NPBO_AN3");
    }
}
