package com.nebula.utils.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import lombok.Data;

public class ObjectMapper_Test {

    @Data
    public static class Bean {
        String name;
    }

    @Test
    public void readValue_content() {
        ObjectMapper_ om = new ObjectMapper_(new ObjectMapper());
        Bean actual = om.readValue("{\"name\": \"test\"}", Bean.class);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("test");
    }
}
