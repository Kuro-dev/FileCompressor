package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.exception.CompressionException;
import com.kurodev.filecompressor.exception.ErrorCode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

/**
 * @author kuro
 **/
public abstract class FileOperationHandler implements Runnable {
    protected final Path source;
    protected final Path dest;
    private Consumer<Path> onDone;

    public Path getSrcFile() {
        return source;
    }

    /**
     * @param source the source file to compress.
     * @param dest   the destination file to generate
     */
    public FileOperationHandler(Path source, Path dest) {

        this.source = source;
        this.dest = dest;
    }

    public Path getDestFile() {
        return dest;
    }

    protected void fail(String reason) {
        throw new CompressionException(reason);
    }

    protected void fail(IOException e) {
        throw new CompressionException(e);
    }

    protected void fail(ErrorCode code) {
        throw new CompressionException(code);
    }

    /**
     * Sets a consumer to be notified once the operation has been successfully executed.
     * The consumer will NOT be notified if the operation fails, as this will result in a RuntimeException.
     */
    public void setOnDone(Consumer<Path> onDone) {
        this.onDone = onDone;
    }

    private void checkFileAccessibility() {
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
            if (onDone != null) {
                onDone.accept(dest);
            }
        } catch (IOException e) {
            fail(e);
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
