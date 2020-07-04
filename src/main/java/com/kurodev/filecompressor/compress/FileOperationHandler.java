package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.exception.CompressionException;
import com.kurodev.filecompressor.exception.ErrorCode;
import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;
import com.kurodev.filecompressor.table.SymbolTable;

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
    protected ProgressCallBack progressCallback;
    private CompressionCallback resultCallback;

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
    public void setResultCallback(CompressionCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    public void setProgressCallback(ProgressCallBack callback) {
        this.progressCallback = callback;
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
            if (resultCallback != null)
                resultCallback.onDone(dest);
        } catch (IOException e) {
            if (resultCallback != null) {
                resultCallback.onFail(e);
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

    protected abstract SymbolTable createSymbolTable(byte[] content);
}
