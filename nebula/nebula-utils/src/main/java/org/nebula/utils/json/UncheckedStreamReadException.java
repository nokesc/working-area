package org.nebula.utils.json;

import com.fasterxml.jackson.core.exc.StreamReadException;

public class UncheckedStreamReadException extends UncheckedJsonProcessingException {

	private static final long serialVersionUID = 1245760652168625787L;

	public UncheckedStreamReadException(StreamReadException cause) {
        super(cause);
    }
}
