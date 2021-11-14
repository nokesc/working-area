package com.nebula.utils.io;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {
    public IORuntimeException(IOException ioe) {
        super(ioe);
    }
}
