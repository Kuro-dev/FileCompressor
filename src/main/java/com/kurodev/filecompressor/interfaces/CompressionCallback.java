package com.kurodev.filecompressor.interfaces;

/**
 * @author kuro
 **/
public interface CompressionCallback {
    void onDone();

    void onFail(Exception e);
}
