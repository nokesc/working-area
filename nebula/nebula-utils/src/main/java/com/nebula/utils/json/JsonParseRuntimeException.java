package com.nebula.utils.json;

import com.fasterxml.jackson.core.JsonParseException;

public class JsonParseRuntimeException extends JsonProcessingRuntimeException {
    public JsonParseRuntimeException(JsonParseException cause) {
        super(cause);
    }
}
