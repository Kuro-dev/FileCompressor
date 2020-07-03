package com.kurodev.filecompressor.exception;

import java.io.IOException;

/**
 * @author kuro
 **/
public class CompressionException extends IOException {
    public CompressionException(ErrorCode code) {
        this("Error " + code.ordinal() + ":" + code.name());
    }

    public CompressionException() {
    }

    public CompressionException(String message) {
        super(message);
    }

    public CompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompressionException(Throwable cause) {
        super(cause);
    }

}
