package com.cnokes.framework.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class JacksonUtils {

    public static final JacksonMapper YAML_MAPPER;
    public static final JacksonMapper JSON_DEFAULT_MAPPER;
    static {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        YAML_MAPPER = new JacksonMapper(objectMapper);
    }

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        JSON_DEFAULT_MAPPER = new JacksonMapper(objectMapper);
    }

    public static final class JacksonMapper {

        private final ObjectMapper objectMapper;

        public JacksonMapper(ObjectMapper objectMapper) {
            super();
            this.objectMapper = objectMapper;
        }

        public final JsonNode readTree(File file) {
            try {
                return objectMapper.readTree(file);
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }

        public final <T> T readValue(File file, Class<T> cl) {
            try {
                return objectMapper.readValue(file, cl);
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }

        public final <T> T readValue(String json, Class<T> cl) {
            try {
                return objectMapper.readValue(json, cl);
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }

        @SuppressWarnings("unchecked")
        public final Map<String, Object> readMap(File file) {
            return readValue(file, Map.class);
        }

        public <T> T readValue(File src, TypeReference<T> valueTypeRef) {
            try {
                return objectMapper.readValue(src, valueTypeRef);
            } catch (IOException e) {
                throw new IORuntimeException(e);
            }
        }

        public String writeValueAsString(Object o) {
            try {
                return objectMapper.writeValueAsString(o);
            } catch (JsonProcessingException e) {
                throw new IORuntimeException(e);
            }
        }

        public <T> T clone(T t) {
            String json = writeValueAsString(t);
            @SuppressWarnings("unchecked")
            Class<T> cl = (Class<T>) t.getClass();
            return readValue(json, cl);
        }
    }
}
