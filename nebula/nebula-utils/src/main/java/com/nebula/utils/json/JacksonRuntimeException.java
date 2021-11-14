package com.nebula.utils.json;

import com.fasterxml.jackson.core.JacksonException;
import com.nebula.utils.io.IORuntimeException;

public class JacksonRuntimeException extends IORuntimeException {

    public JacksonRuntimeException(JacksonException cause) {
        super(cause);
    }
}
