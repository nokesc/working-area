package com.cnokes.infra_service.network;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class PrefixLengthTest {

    @Test
    public void ALL() {
        assertEquals(33, PrefixLength.ALL.size());
    }

    @ParameterizedTest
    @MethodSource()
    public void validateData(PrefixLength prefixLength) {
        //System.out.printf("%s %s\n", prefixLength.getValue(), prefixLength.getUsableHosts());
        
        long addresses = (long) Math.pow(2, 32 - prefixLength.getValue());
        assertEquals(prefixLength.getHosts(), addresses);
        assertEquals(prefixLength.getUsableHosts(), Math.max(0, addresses - 2));

        StringBuilder[] octets = new StringBuilder[] { new StringBuilder(8), new StringBuilder(8), new StringBuilder(8),
                new StringBuilder(8) };
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 8; i++) {
                int k = (j * 8) + (i + 1);
                // System.out.println("position = " + k);
                if (k <= prefixLength.getValue()) {
                    octets[j].append("1");
                } else {
                    octets[j].append("0");
                }
            }
        }
        String expectedNetmask = String.format("%s.%s.%s.%s", Long.parseLong(octets[0].toString(), 2),
                Long.parseLong(octets[1].toString(), 2), Long.parseLong(octets[2].toString(), 2),
                Long.parseLong(octets[3].toString(), 2));
        assertEquals(expectedNetmask, prefixLength.getNetmask());
    }

    static List<PrefixLength> validateData() {
        return PrefixLength.ALL;
    }

    // @Test
    // public void abc() {
    // long a = 95;
    // System.out.println(a);

    // long b = 83 * 256;
    // System.out.println(b);

    // long c = 58L * (256 * 256);
    // System.out.println(c);

    // long d = 172L * (256 * 256 * 256);
    // System.out.println(d);

    // long e = a + b + c + d;
    // assertEquals(2889503583L, e);
    // }
}
