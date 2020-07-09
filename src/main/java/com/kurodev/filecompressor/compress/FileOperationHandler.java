package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;
import com.kurodev.filecompressor.table.SymbolTable;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * @author kuro
 **/
public abstract class FileOperationHandler implements Runnable {
    protected final Logger logger = Logger.getLogger(this.getClass());
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

    protected static String humanReadableByteCount(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

    @Override
    public void run() {
        try {
            logger.debug("Starting " + this.getClass().getSimpleName());
            logger.debug("Aprox input size: " + humanReadableByteCount(source.available()));
            prepare();
            work();
            logger.debug("operation finished");
            cleanup();
            if (resultCallback != null)
                resultCallback.onDone();
        } catch (Exception e) {
            logger.error("Compression failed", e);
            if (resultCallback != null) {
                resultCallback.onFail(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void prepare() throws Exception;

    protected abstract void work() throws IOException;

    protected abstract SymbolTable createSymbolTable(InputStream content) throws IOException;

    protected void cleanup() throws IOException {
        source.close();
        dest.close();
    }
}
