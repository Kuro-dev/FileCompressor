package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;
import com.kurodev.filecompressor.table.SymbolTable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author kuro
 **/
public abstract class FileOperationHandler implements Runnable {
    protected final InputStream source;
    protected final OutputStream dest;
    protected ProgressCallBack progressCallback;
    private CompressionCallback resultCallback;

    /**
     * @param source the source file to compress.
     * @param dest   the destination file to generate
     */
    protected FileOperationHandler(InputStream source, OutputStream dest) {
        this.source = source;
        this.dest = dest;
    }



    /**
     * Sets a consumer to be notified once the operation has been successfully executed.
     * The consumer will NOT be notified if the operation fails, as this will result in a RuntimeException.
     */
    public void setResultCallback(CompressionCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    public void setProgressCallback(ProgressCallBack callback) {
        this.progressCallback = callback;
    }

    @Override
    public void run() {
        try {
            work();
            if (resultCallback != null)
                resultCallback.onDone();
        } catch (IOException e) {
            if (resultCallback != null) {
                resultCallback.onFail(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void work() throws IOException;

    protected abstract SymbolTable createSymbolTable(InputStream content) throws IOException;
}
