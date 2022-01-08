package org.nebula.io;

import java.io.IOException;
import java.io.UncheckedIOException;

public class FlushRuntimeException extends UncheckedIOException {
	private static final long serialVersionUID = 7006040394248040018L;

	public FlushRuntimeException(IOException ioe) {
        super(ioe);
    }
}
