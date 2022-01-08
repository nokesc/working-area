package org.nebula.utils.json;

import com.fasterxml.jackson.core.JsonParseException;

public class UncheckedJsonParseException extends UncheckedStreamReadException {
	private static final long serialVersionUID = -2993003515906903925L;

	public UncheckedJsonParseException(JsonParseException cause) {
        super(cause);
    }
}
