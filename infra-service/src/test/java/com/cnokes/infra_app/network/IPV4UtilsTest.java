package com.cnokes.infra_app.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.cnokes.infra_app.network.service.IPV4Utils;
import com.cnokes.infra_app.network.service.IPV4Utils.CIDRBlock;
import com.cnokes.infra_app.network.service.PrefixLength;

import org.junit.jupiter.api.Test;

public class IPV4UtilsTest {
    @Test
    public void toLong() {
        String ipv4Address = "172.58.83.95";
        long addressAsLong = 2889503583L;
        assertEquals(addressAsLong, IPV4Utils.toLong(ipv4Address));
        assertEquals(ipv4Address,IPV4Utils.toAddress(addressAsLong));
    }

    @Test
    public void getOctets() {
        List<Integer> octets = IPV4Utils.toOctets("172.58.83.95");
        assertEquals(172, octets.get(0));
        assertEquals(58, octets.get(1));
        assertEquals(83, octets.get(2));
        assertEquals(95, octets.get(3));
    }

    @Test
    public void toCIDRBlock() {
        String cidrBlockVal = IPV4Utils.MaxPrivateNetworkCIDRBlocks._192_168_0_0__16;
        CIDRBlock cidrBlock = IPV4Utils.toCIDRBlock(cidrBlockVal);
        assertEquals(cidrBlockVal, cidrBlock.getValue());
        assertEquals("192.168.0.0", cidrBlock.getAddress());
        assertEquals(PrefixLength._16, cidrBlock.getPrefixLength());
    }

    @Test
    public void nextBlock() {
        CIDRBlock cidrBlock = IPV4Utils.toCIDRBlock("192.168.0.0/24");
        CIDRBlock nextBlock = cidrBlock.nextBlock();
        assertEquals("192.168.1.0/24", nextBlock.getValue());
    }

    @Test
    public void toCIDRBlock_notACidrBlock() {
        String cidrBlockVal = "192.168.0.1/16";
        assertThrows(IllegalArgumentException.class, () -> {
            IPV4Utils.toCIDRBlock(cidrBlockVal);
        });
    }

    @Test
    public void toCIDRBlock_notCidrNotation() {
        assertThrows(IllegalArgumentException.class, () -> {
            IPV4Utils.toCIDRBlock("abc");
        });
    }
}
