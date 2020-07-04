package com.kurodev.filecompressor.interfaces;

import java.io.IOException;

/**
 * @author kuro
 **/
public interface CompressionCallback {
    void onDone();

    void onFail(IOException e);
}
