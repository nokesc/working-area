package org.nebula.example;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {

    public IORuntimeException(IOException e) {
        super(e);
    }

}
