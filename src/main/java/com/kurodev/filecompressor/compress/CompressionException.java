package com.kurodev.filecompressor.compress;

/**
 * @author kuro
 **/
public class CompressionException extends RuntimeException{

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

    public CompressionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
