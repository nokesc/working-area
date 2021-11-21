package com.cnokes.infra_app.network.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;

import lombok.Getter;

public class IPV4Utils {
    public static class MaxPrivateNetworkCIDRBlocks {
        public static final String _10_0_0_0__8 = "10.0.0.0/8";
        public static final String _172_16_0_0__12 = "172.16.0.0/12";
        public static final String _192_168_0_0__16 = "192.168.0.0/16";

        public static final List<String> ALL = Collections
                .unmodifiableList(Arrays.asList(_10_0_0_0__8, _172_16_0_0__12, _192_168_0_0__16));
    }

    public static List<Integer> toOctets(String ipv4Address) {
        if (ipv4Address.length() > 15) {
            throw new IllegalArgumentException(ipv4Address);
        }
        String[] particles = ipv4Address.split("\\.");
        if (particles.length != 4) {
            throw new IllegalArgumentException(ipv4Address);
        }
        List<Integer> response = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            String particle = particles[i];
            try {
                int val = Integer.parseInt(particle);
                if (val < 0 || val > 255) {
                    throw new IllegalArgumentException(ipv4Address);
                }
                response.add(val);
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException(ipv4Address, nfe);
            }
        }
        return Collections.unmodifiableList(response);
    }

    private static final long[] OCTET_MULTIPLIERS = { 256L * 256L * 256L, 256 * 256, 256, 1 };

    public static long toLong(String ipv4Address) {
        return toLong(toOctets(ipv4Address));
    }

    public static long toLong(List<Integer> octets) {
        return octets.get(0) * OCTET_MULTIPLIERS[0] //
                + octets.get(1) * OCTET_MULTIPLIERS[1] //
                + octets.get(2) * OCTET_MULTIPLIERS[2] //
                + octets.get(3) * OCTET_MULTIPLIERS[3];
    }

    private static int[] toOctets(final long packed) {
        final int ret[] = new int[4];
        for (int i = 3; i >= 0; --i) {
            ret[i] |= packed >>> 8 * (3 - i) & 0xff;
        }
        return ret;
    }

    public static String toAddress(long packed) {
        int[] ret = toOctets(packed);
        return ret[0] + "." //
                + ret[1] + "." //
                + ret[2] + "." //
                + ret[3];
    }

    public static CIDRBlock tryCIDRBlock(String cidrNotation) {
        try {
            return toCIDRBlock(cidrNotation);
        } catch (RuntimeException re) {
            return null;
        }
    }

    public static CIDRBlock toCIDRBlock(String cidrNotation) {
        SubnetUtils subnetUtils = new SubnetUtils(cidrNotation);
        SubnetInfo info = subnetUtils.getInfo();
        if (!info.getNetworkAddress().equals(subnetUtils.getInfo().getAddress())) {
            throw new IllegalArgumentException("Not a CIDR Block: " + cidrNotation);
        }
        String[] particles = cidrNotation.split("/");
        if (particles.length != 2) {
            throw new IllegalArgumentException(cidrNotation);
        }
        String address = particles[0];
        PrefixLength prefixLength = PrefixLength.get(Integer.parseInt(particles[1]));

        List<Integer> octets = toOctets(address);
        return new CIDRBlock(cidrNotation, address, octets, prefixLength, subnetUtils);
    }

    @Getter
    public static final class CIDRBlock {
        private final String value;
        private final String address;
        private final Long addressAsLong;
        private final List<Integer> octets;
        private final PrefixLength prefixLength;
        private final SubnetUtils subnetUtils;

        private CIDRBlock(String value, String address, List<Integer> octets, PrefixLength prefixLength,
                SubnetUtils subnetUtils) {
            this.value = value;
            this.address = address;
            this.addressAsLong = IPV4Utils.toLong(octets);
            this.octets = Collections.unmodifiableList(octets);
            this.prefixLength = prefixLength;
            this.subnetUtils = subnetUtils;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CIDRBlock ? value.equals(((CIDRBlock) obj).getValue()) : false;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        public String toString() {
            return value;
        }

        public long getLastAddressAsLong() {
            return getAddressAsLong() + getPrefixLength().getUsableHosts() + 1;
        }

        public CIDRBlock nextBlock() {
            long nextBlockStart = getLastAddressAsLong() + 1;
            return toCIDRBlock(toAddress(nextBlockStart) + "/" + prefixLength.getValue());
        }

        public CIDRBlock toPrefixLength(PrefixLength newPrefixLength) {
            return toCIDRBlock(address + "/" + newPrefixLength.getValue());
        }
    }
}
