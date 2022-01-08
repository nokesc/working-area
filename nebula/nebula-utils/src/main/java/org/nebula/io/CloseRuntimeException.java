package org.nebula.io;

import java.io.IOException;
import java.io.UncheckedIOException;

public class CloseRuntimeException extends UncheckedIOException {
	private static final long serialVersionUID = 2783289940147182949L;

	public CloseRuntimeException(IOException ioe) {
        super(ioe);
    }
}
