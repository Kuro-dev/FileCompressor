package com.kurodev.filecompressor.exception;

/**
 * @author kuro
 **/
public class DecompressionException extends CompressionException {
    public DecompressionException(ErrorCode code) {
        super(code);
    }

    public DecompressionException() {
        super();
    }

    public DecompressionException(String message) {
        super(message);
    }

    public DecompressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecompressionException(Throwable cause) {
        super(cause);
    }
}
