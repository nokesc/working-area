package com.cnokes.learning;

import java.util.HashMap;

import com.cnokes.infra_service.network.repo.model.NetworkPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

import de.cronn.reflection.util.ClassUtils;
import de.cronn.reflection.util.PropertyGetter;
import lombok.AllArgsConstructor;

public class SpringErrorsLearningTest {

	static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	MapBindingResult errors = new MapBindingResult(new HashMap<>(), "root");

	@Test
	public void test1() throws JsonProcessingException {
		errors.rejectValue("a", "code1");
		errors.addError(new FieldError("obj2", "b", "message"));
		errors.pushNestedPath("y3");
		errors.rejectValue("b", "code2");

		errors.pushNestedPath("y4");
		errors.rejectValue("c", "code3");

		errors.addError(new ObjectError("myobj", "my message"));

		System.out.println(mapper.writeValueAsString(errors.getFieldErrors()));
		System.out.println(mapper.writeValueAsString(errors.getGlobalErrors()));
	}

	@Test
	public void test2() throws JsonProcessingException {
		NetworkPlan networkPlan = new NetworkPlan();

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(networkPlan, "networkPlan");
		ValidationUtils.rejectIfEmpty(errors, "id", "e1");

		System.out.println(mapper.writeValueAsString(errors.getFieldErrors()));
		System.out.println(mapper.writeValueAsString(errors.getGlobalErrors()));
	}

	@ParameterizedTest
	@CsvSource({ "a", "b", "c", "d" })

	public void test3() throws JsonProcessingException {
		NetworkPlan networkPlan = new NetworkPlan();
		networkPlan.setId("myId");

		ObjectContext<NetworkPlan> $networkPlan = new ObjectContext<>(networkPlan);
		$networkPlan.validate(NetworkPlan::getId);

	}

	@AllArgsConstructor
	public static class ObjectContext<T> {
		private final T target;

//		public void validate(TypedPropertyGetter<T, String> typedPropertyGetter) {
		public void validate(PropertyGetter<T> pg) {
//			PropertyGetter<T> pg = bean -> typedPropertyGetter.get(bean);
			String methodName = ClassUtils.getMethodName((Class<T>) target.getClass(), pg);
//			String methodName = PropertyUtils.getPropertyName((Class<T>) target.getClass(), propertyGetter);
			String value = String.class.cast(pg.get(target));
			System.out.printf("%s=%s\n", methodName, value);

//			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(target, "networkPlan");
//
//			ValidationUtils.rejectIfEmpty(errors, "id", "e1");
//
//			System.out.println(mapper.writeValueAsString(errors.getFieldErrors()));
//			System.out.println(mapper.writeValueAsString(errors.getGlobalErrors()));
		}
	}
}
