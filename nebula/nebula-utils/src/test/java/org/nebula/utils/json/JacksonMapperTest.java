package org.nebula.utils.json;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.nebula.utils.json.JacksonMapper.JSON_MAPPER;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.experimental.Accessors;

public class JacksonMapperTest {

	@Data
	@Accessors(chain = true)
	public static class Bean {
		String name;
		Inner inner;
		List<Inner> myList;
	}

	@Data
	@Accessors(chain = true)
	public static class Inner {
		String name;
		List<String> values;
	}

	@Test
	public void readValue_content() {
		JacksonMapper om = new JacksonMapper(new ObjectMapper());
		Bean actual = om.readValue("{\"name\": \"test\"}", Bean.class);
		assertThat(actual).isNotNull();
		assertThat(actual.getName()).isEqualTo("test");
	}

	@Test
	public void writeValueAsString() {
		Bean bean = new Bean().setName("abc");
		bean.setInner(new Inner().setName("def").setValues(asList("1", "2")));
		bean.setMyList(asList(new Inner(), new Inner().setName("inner")));
		System.out.println(JSON_MAPPER.writeValueAsString(bean));
	}
}
