package org.nebula.utils.json;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.net.URL;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JacksonMapper {

	public static final JacksonMapper YAML_MAPPER;
	public static final JacksonMapper JSON_MAPPER;
	static {
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.findAndRegisterModules();
		YAML_MAPPER = new JacksonMapper(objectMapper);
	}

	static {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		JSON_MAPPER = new JacksonMapper(objectMapper);
	}

	@FunctionalInterface
	public interface ObjectMapperRunner {
		void run() throws JacksonException, IOException;
	}

	@FunctionalInterface
	public interface ObjectMapperSupplier<R> {
		R get() throws JacksonException, IOException;
	}

	@FunctionalInterface
	public interface ObjectMapperFunction<R> {
		R apply(ObjectMapper om) throws JacksonException, IOException;
	}

	@FunctionalInterface
	public interface ObjectMapperConsumer {
		void accept(ObjectMapper om) throws JacksonException, IOException;
	}

	public static void run(ObjectMapperRunner runner) {
		try {
			runner.run();
		} catch (JacksonException e) {
			throw UncheckedJacksonException.wrapUnchecked(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static <R> R get(ObjectMapperSupplier<R> supplier) {
		try {
			return supplier.get();
		} catch (JacksonException e) {
			throw UncheckedJacksonException.wrapUnchecked(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private final ObjectMapper target;

	public void accept(ObjectMapperConsumer consumer) {
		run(() -> consumer.accept(target));
	}

	public <R> R apply(ObjectMapperFunction<R> function) {
		return get(() -> function.apply(target));
	}

	public <T> T readValue(File src, Class<T> valueType) {
		return get(() -> target.readValue(src, valueType));
	}

	public <T> T readValue(File src, TypeReference<T> valueTypeRef) {
		return get(() -> target.readValue(src, valueTypeRef));
	}

	public <T> T readValue(URL src, Class<T> valueType) {
		return get(() -> target.readValue(src, valueType));
	}

	public <T> T readValue(URL src, TypeReference<T> valueTypeRef) {
		return get(() -> target.readValue(src, valueTypeRef));
	}

	public <T> T readValue(String content, Class<T> valueType) {
		return get(() -> target.readValue(content, valueType));
	}

	public <T> T readValue(String content, TypeReference<T> valueTypeRef) {
		return get(() -> target.readValue(content, valueTypeRef));
	}

	public <T> T readValue(Reader src, Class<T> valueType) {
		return get(() -> target.readValue(src, valueType));
	}

	public <T> T readValue(Reader src, TypeReference<T> valueTypeRef) {
		return get(() -> target.readValue(src, valueTypeRef));
	}

	public void writeValue(File resultFile, Object value) {
		run(() -> target.writeValue(resultFile, value));
	}

	public void writeValue(Writer w, Object value) {
		run(() -> target.writeValue(w, value));
	}

	public String writeValueAsString(Object value) {
		return get(() -> target.writeValueAsString(value));
	}

	public <T> T clone(T t) {
		String json = writeValueAsString(t);
		@SuppressWarnings("unchecked")
		Class<T> cl = (Class<T>) t.getClass();
		return readValue(json, cl);
	}
}
