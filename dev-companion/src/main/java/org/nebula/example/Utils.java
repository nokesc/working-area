package org.nebula.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hubspot.jinjava.loader.FileLocator;

public class Utils {
    public static final File WORKING_DIR = getCanonicalFile(new File("."));

    public static final JacksonMapper YAML_MAPPER;
    static {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        YAML_MAPPER = new JacksonMapper(objectMapper);
    }

    public static final File getCanonicalFile(File f) {
        try {
            return f.getCanonicalFile();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static final File workingDir(String path) {
        return new File(WORKING_DIR, path);
    }

    public static final String readString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static class Jinjava {
        public static final FileLocator fileLocator(File file) {
            try {
                return new FileLocator(file);
            } catch (FileNotFoundException e) {
                throw new IORuntimeException(e);
            }
        }
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
    }
}
