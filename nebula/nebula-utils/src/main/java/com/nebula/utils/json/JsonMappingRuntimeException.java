package com.nebula.utils.json;

import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonMappingRuntimeException extends JsonProcessingRuntimeException {
    public JsonMappingRuntimeException(JsonMappingException cause) {
        super(cause);
    }
}
