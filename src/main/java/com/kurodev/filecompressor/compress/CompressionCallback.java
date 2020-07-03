package com.kurodev.filecompressor.compress;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author kuro
 **/
public interface CompressionCallback {
    void onDone(Path path);

    void onFail(IOException ex);
}
