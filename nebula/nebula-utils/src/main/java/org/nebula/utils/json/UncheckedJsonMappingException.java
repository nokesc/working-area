package org.nebula.utils.json;

import com.fasterxml.jackson.databind.JsonMappingException;

public class UncheckedJsonMappingException extends UncheckedJsonProcessingException {
	private static final long serialVersionUID = 1507743476530771015L;

	public UncheckedJsonMappingException(JsonMappingException cause) {
        super(cause);
    }
}
