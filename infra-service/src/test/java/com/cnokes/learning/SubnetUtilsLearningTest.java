package com.cnokes.learning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.junit.jupiter.api.Test;

public class SubnetUtilsLearningTest {

   @Test
    public void info() {
        SubnetUtils subnetUtils = new SubnetUtils("172.16.0.0/24");
        assertEquals(false, subnetUtils.isInclusiveHostCount());

        SubnetInfo info = subnetUtils.getInfo();
        System.out.println(info);
        assertEquals("172.16.0.0/24", info.getCidrSignature());
        assertEquals("255.255.255.0", info.getNetmask());
        assertEquals("172.16.0.0", info.getNetworkAddress());
        assertEquals("172.16.0.255", info.getBroadcastAddress());
        assertEquals("172.16.0.1", info.getLowAddress());
        assertEquals("172.16.0.254", info.getHighAddress());
        assertEquals(254, info.getAddressCountLong());
        assertEquals(-1408237545, info.asInteger("172.16.0.23"));        

        System.out.println((long)-1408237545);
        
        subnetUtils.setInclusiveHostCount(true);
        assertEquals(true, subnetUtils.isInclusiveHostCount());
        System.out.println(info);
        assertEquals("172.16.0.0/24", info.getCidrSignature());
        assertEquals("255.255.255.0", info.getNetmask());
        assertEquals("172.16.0.0", info.getNetworkAddress());
        assertEquals("172.16.0.255", info.getBroadcastAddress());
        assertEquals("172.16.0.0", info.getLowAddress());
        assertEquals("172.16.0.255", info.getHighAddress());
        assertEquals(256, info.getAddressCountLong());

        subnetUtils = new SubnetUtils("172.16.0.23/24");
        assertEquals(false, subnetUtils.isInclusiveHostCount());

        info = subnetUtils.getInfo();
        System.out.println(info);
        assertEquals("172.16.0.23/24", info.getCidrSignature());
        assertEquals("255.255.255.0", info.getNetmask());
        assertEquals("172.16.0.0", info.getNetworkAddress());
        assertEquals("172.16.0.255", info.getBroadcastAddress());
        assertEquals("172.16.0.1", info.getLowAddress());
        assertEquals("172.16.0.254", info.getHighAddress());
        assertEquals(254, info.getAddressCountLong());
    }

    @Test
    public void getNextSubnet() {
        SubnetUtils subnetUtils = new SubnetUtils("172.16.0.0/24");
        subnetUtils.setInclusiveHostCount(true);
        SubnetInfo info = subnetUtils.getInfo();
        System.out.println(info);
        System.out.println(info.getBroadcastAddress());
        SubnetUtils highAddress = new SubnetUtils(info.getHighAddress() + "/24");
        SubnetUtils nextSubnet = highAddress.getNext();

        
        System.out.println(nextSubnet.getInfo());
    }

    @Test
    public void smallSubnet() {
        SubnetUtils subnetUtils = new SubnetUtils("172.16.0.23/30");
        SubnetInfo info = subnetUtils.getInfo();
        System.out.println(info);
        assertEquals("172.16.0.23/30", info.getCidrSignature());
        assertEquals("255.255.255.252", info.getNetmask());
        assertEquals("172.16.0.20", info.getNetworkAddress());
        assertEquals("172.16.0.23", info.getBroadcastAddress());
        assertEquals("172.16.0.21", info.getLowAddress());
        assertEquals("172.16.0.22", info.getHighAddress());
        assertEquals(2, info.getAddressCountLong());
    }
}
