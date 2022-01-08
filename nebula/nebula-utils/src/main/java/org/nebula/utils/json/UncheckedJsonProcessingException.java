package org.nebula.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public class UncheckedJsonProcessingException extends UncheckedJacksonException {
	private static final long serialVersionUID = -1861182916439541514L;

	public UncheckedJsonProcessingException(JsonProcessingException cause) {
        super(cause);
    }
}
