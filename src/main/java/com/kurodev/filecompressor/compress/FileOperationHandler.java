package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.exception.CompressionException;
import com.kurodev.filecompressor.exception.ErrorCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author kuro
 **/
public abstract class FileOperationHandler implements Runnable {
    protected final Path source;
    protected final Path dest;
    private CompressionCallback callback;

    /**
     * @param source the source file to compress.
     * @param dest   the destination file to generate
     */
    public FileOperationHandler(Path source, Path dest) {

        this.source = source;
        this.dest = dest;
    }

    public Path getSrcFile() {
        return source;
    }

    public Path getDestFile() {
        return dest;
    }

    protected void fail(String reason) throws CompressionException {
        throw new CompressionException(reason);
    }

    protected void fail(IOException e) throws CompressionException {
        throw new CompressionException(e);
    }

    protected void fail(ErrorCode code) throws CompressionException {
        throw new CompressionException(code);
    }

    /**
     * Sets a consumer to be notified once the operation has been successfully executed.
     * The consumer will NOT be notified if the operation fails, as this will result in a RuntimeException.
     */
    public void setCallback(CompressionCallback callback) {
        this.callback = callback;
    }

    private void checkFileAccessibility() throws CompressionException {
        if (!Files.isRegularFile(source))
            fail("Must be a file");
        if (!Files.isReadable(source))
            fail("Cannot read file");
    }

    @Override
    public void run() {
        try {
            checkFileAccessibility();
            work();
            if (callback != null)
                callback.onDone(dest);
        } catch (IOException e) {
            if (callback != null) {
                callback.onFail(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    protected void writeFile(byte[] bytes) throws IOException {
        if (!Files.exists(dest)) {
            Files.createFile(dest);
        }
        Files.write(dest, bytes, StandardOpenOption.WRITE);
    }

    protected abstract void work() throws IOException;
}
