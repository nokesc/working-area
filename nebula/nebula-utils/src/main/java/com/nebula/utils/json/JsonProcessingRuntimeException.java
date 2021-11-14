package com.nebula.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonProcessingRuntimeException extends JacksonRuntimeException {
    public JsonProcessingRuntimeException(JsonProcessingException cause) {
        super(cause);
    }
}
