package com.cnokes.infra_service.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class PrefixLength {

    private static final List<PrefixLength> PREFIX_LENGTHS_SETUP = new ArrayList<>();
    private static final PrefixLength[] INDEX = new PrefixLength[33];

    public static final PrefixLength get(int value) {
        return INDEX[value];
    }

    public static final PrefixLength _32 = new PrefixLength(32, "255.255.255.255", 1);
    public static final PrefixLength _31 = new PrefixLength(31, "255.255.255.254", 2);
    public static final PrefixLength _30 = new PrefixLength(30, "255.255.255.252", 4);
    public static final PrefixLength _29 = new PrefixLength(29, "255.255.255.248", 8);
    public static final PrefixLength _28 = new PrefixLength(28, "255.255.255.240", 16);
    public static final PrefixLength _27 = new PrefixLength(27, "255.255.255.224", 32);
    public static final PrefixLength _26 = new PrefixLength(26, "255.255.255.192", 64);
    public static final PrefixLength _25 = new PrefixLength(25, "255.255.255.128", 128);
    public static final PrefixLength _24 = new PrefixLength(24, "255.255.255.0", 256);
    public static final PrefixLength _23 = new PrefixLength(23, "255.255.254.0", 512);
    public static final PrefixLength _22 = new PrefixLength(22, "255.255.252.0", 1024);
    public static final PrefixLength _21 = new PrefixLength(21, "255.255.248.0", 2048);
    public static final PrefixLength _20 = new PrefixLength(20, "255.255.240.0", 4096);
    public static final PrefixLength _19 = new PrefixLength(19, "255.255.224.0", 8192);
    public static final PrefixLength _18 = new PrefixLength(18, "255.255.192.0", 16384);
    public static final PrefixLength _17 = new PrefixLength(17, "255.255.128.0", 32768);
    public static final PrefixLength _16 = new PrefixLength(16, "255.255.0.0", 65536);
    public static final PrefixLength _15 = new PrefixLength(15, "255.254.0.0", 131072);
    public static final PrefixLength _14 = new PrefixLength(14, "255.252.0.0", 262144);
    public static final PrefixLength _13 = new PrefixLength(13, "255.248.0.0", 524288);
    public static final PrefixLength _12 = new PrefixLength(12, "255.240.0.0", 1048576);
    public static final PrefixLength _11 = new PrefixLength(11, "255.224.0.0", 2097152);
    public static final PrefixLength _10 = new PrefixLength(10, "255.192.0.0", 4194304);
    public static final PrefixLength _9 = new PrefixLength(9, "255.128.0.0", 8388608);
    public static final PrefixLength _8 = new PrefixLength(8, "255.0.0.0", 16777216);
    public static final PrefixLength _7 = new PrefixLength(7, "254.0.0.0", 33554432);
    public static final PrefixLength _6 = new PrefixLength(6, "252.0.0.0", 67108864);
    public static final PrefixLength _5 = new PrefixLength(5, "248.0.0.0", 134217728);
    public static final PrefixLength _4 = new PrefixLength(4, "240.0.0.0", 268435456);
    public static final PrefixLength _3 = new PrefixLength(3, "224.0.0.0", 536870912);
    public static final PrefixLength _2 = new PrefixLength(2, "192.0.0.0", 1073741824);
    public static final PrefixLength _1 = new PrefixLength(1, "128.0.0.0", 2147483648L);
    public static final PrefixLength _0 = new PrefixLength(0, "0.0.0.0", 4294967296L);

    public static final List<PrefixLength> ALL;
    static {
        ALL = Collections.unmodifiableList(PREFIX_LENGTHS_SETUP);
    }

    private final int value;
    private final String netmask;
    private final long hosts;
    private final long usableHosts;

    private PrefixLength(int value, String netmask, long hosts) {
        this.value = value;
        this.netmask = netmask;
        this.hosts = hosts;
        this.usableHosts = Math.max(0, hosts - 2);
        PREFIX_LENGTHS_SETUP.add(this);
        INDEX[value] = this;
    }
}
