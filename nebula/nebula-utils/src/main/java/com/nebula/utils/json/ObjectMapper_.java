package com.nebula.utils.json;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
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
    interface Jackson1ArgFunction<T, P1, R> {
        R apply(T om, P1 p1) throws JsonMappingException, JsonParseException, IOException;
    }

    @FunctionalInterface
    interface Jackson2ArgFunction<T, P1, P2, R> {
        R apply(T om, P1 p1, P2 p2) throws JsonMappingException, JsonParseException, IOException;
    }

    @FunctionalInterface
    interface Jackson2ArgVoidFunction<T, P1, P2> {
        void apply(T om, P1 p1, P2 p2) throws JsonMappingException, JsonParseException, IOException;
    }

    private final ObjectMapper target;

    public static <T, P1, R> R wrapCE(T t, Jackson1ArgFunction<T, P1, R> function, P1 p1) {
        try {
            return function.apply(t, p1);
        } catch (JsonMappingException e) {
            throw new JsonMappingRuntimeException(e);
        } catch (JsonParseException e) {
            throw new JsonParseRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static <T, P1, P2, R> R wrapCE(T t, Jackson2ArgFunction<T, P1, P2, ? extends R> function, P1 p1, P2 p2) {
        try {
            return function.apply(t, p1, p2);
        } catch (JsonMappingException e) {
            throw new JsonMappingRuntimeException(e);
        } catch (JsonParseException e) {
            throw new JsonParseRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static <T, P1, P2> void wrapCEVoid(T t, Jackson2ArgVoidFunction<T, P1, P2> function, P1 p1, P2 p2) {
        try {
            function.apply(t, p1, p2);
        } catch (JsonMappingException e) {
            throw new JsonMappingRuntimeException(e);
        } catch (JsonParseException e) {
            throw new JsonParseRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public <T> T readValue(File src, Class<T> valueType) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueType);
    }

    public <T> T readValue(File src, TypeReference<T> valueTypeRef) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueTypeRef);
    }

    public <T> T readValue(URL src, Class<T> valueType) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueType);
    }

    public <T> T readValue(URL src, TypeReference<T> valueTypeRef) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueTypeRef);
    }

    public <T> T readValue(String content, Class<T> valueType) {
        return wrapCE(target, ObjectMapper::<T>readValue, content, valueType);
    }

    public <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        return wrapCE(target, ObjectMapper::<T>readValue, content, valueTypeRef);
    }

    public <T> T readValue(Reader src, Class<T> valueType) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueType);
    }

    public <T> T readValue(Reader src, TypeReference<T> valueTypeRef) {
        return wrapCE(target, ObjectMapper::<T>readValue, src, valueTypeRef);
    }

    public void writeValue(File resultFile, Object value) {
        wrapCEVoid(target, ObjectMapper::writeValue, resultFile, value);
    }

    public void writeValue(Writer w, Object value) {
        wrapCEVoid(target, ObjectMapper::writeValue, w, value);
    }

    public String writeValueAsString(Object value) {
        return wrapCE(target, ObjectMapper::writeValueAsString, value);
    }
}
