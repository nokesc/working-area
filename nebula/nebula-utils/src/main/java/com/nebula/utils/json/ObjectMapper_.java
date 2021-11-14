package com.nebula.utils.json;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nebula.utils.io.IORuntimeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ObjectMapper_ {

    @FunctionalInterface
    interface ObjectMapper2ArgFunction<P1, P2, R> {
        R apply(ObjectMapper om, P1 p1, P2 p2) throws JsonMappingException, JsonParseException, IOException;
    }

    @FunctionalInterface
    interface ObjectMapper2ArgVoidFunction<P1, P2> {
        void apply(ObjectMapper om, P1 p1, P2 p2) throws JsonMappingException, JsonParseException, IOException;
    }

    private final ObjectMapper target;

    public <P1, P2, R> R invokeAndWrapChecked(ObjectMapper2ArgFunction<P1, P2, R> function, P1 p1, P2 p2) {
        try {
            return function.apply(target, p1, p2);
        } catch (JsonMappingException e) {
            throw new JsonMappingRuntimeException(e);
        } catch (JsonParseException e) {
            throw new JsonParseRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public <P1, P2> void invokeAndWrapChecked(ObjectMapper2ArgVoidFunction<P1, P2> function, P1 p1, P2 p2) {
        try {
            function.apply(target, p1, p2);
        } catch (JsonMappingException e) {
            throw new JsonMappingRuntimeException(e);
        } catch (JsonParseException e) {
            throw new JsonParseRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public <T> T readValue(File src, Class<T> valueType) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<File, Class<T>, T>) ObjectMapper::readValue, src,
                valueType);
    }

    public <T> T readValue(File src, TypeReference<T> valueTypeRef) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<File, TypeReference<T>, T>) ObjectMapper::readValue, src,
                valueTypeRef);
    }

    public <T> T readValue(URL src, Class<T> valueType) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<URL, Class<T>, T>) ObjectMapper::readValue, src,
                valueType);
    }

    public <T> T readValue(URL src, TypeReference<T> valueTypeRef) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<URL, TypeReference<T>, T>) ObjectMapper::readValue, src,
                valueTypeRef);
    }

    public <T> T readValue(String content, Class<T> valueType) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<String, Class<T>, T>) ObjectMapper::readValue, content,
                valueType);
    }

    public <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<String, TypeReference<T>, T>) ObjectMapper::readValue,
                content, valueTypeRef);
    }

    public <T> T readValue(Reader src, Class<T> valueType) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<Reader, Class<T>, T>) ObjectMapper::readValue, src,
                valueType);
    }

    public <T> T readValue(Reader src, TypeReference<T> valueTypeRef) {
        return invokeAndWrapChecked((ObjectMapper2ArgFunction<Reader, TypeReference<T>, T>) ObjectMapper::readValue,
                src, valueTypeRef);
    }

    public void writeValue(File resultFile, Object value) {
        invokeAndWrapChecked((ObjectMapper2ArgVoidFunction<File, Object>) ObjectMapper::writeValue, resultFile, value);
    }

    public void writeValue(Writer w, Object value) {
        invokeAndWrapChecked((ObjectMapper2ArgVoidFunction<Writer, Object>) ObjectMapper::writeValue, w, value);
    }

    public String writeValueAsString(Object value) {
        try {
            return target.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonProcessingRuntimeException(e);
        }
    }
}
