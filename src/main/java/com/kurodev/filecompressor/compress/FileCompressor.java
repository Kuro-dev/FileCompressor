package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.util.ByteUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Consumer;

/**
 * @author kuro
 **/
public class FileCompressor implements Runnable {
    private static final String COMPRESSED_FILE_EXTENSION = ".cmp";
    private final Path source;
    private final Path dest;
    private Consumer<Path> onDone;

    /**
     * @param source the source file to compress.
     */
    public FileCompressor(Path source) {
        this(source, autogenDestFile(source));
    }

    /**
     * @param source the source file to compress.
     * @param dest   the destination file to generate
     */
    public FileCompressor(Path source, Path dest) {

        this.source = source;
        this.dest = dest;
    }

    private static Path autogenDestFile(Path source) {
        Path parent = source.getParent();
        Path filenameAsPath = source.getFileName();
        String fileName = filenameAsPath.toString();
        return Paths.get(parent.toString() + "/" + fileName + COMPRESSED_FILE_EXTENSION);
    }

    public Path getDestFile() {
        return dest;
    }

    private void checkCompressibility() {
        if (!Files.isRegularFile(source))
            fail("Must be a file");
        if (!Files.isReadable(source))
            fail("Cannot read file");
    }

    @Override
    public void run() {
        try {
            checkCompressibility();
            final String content = Files.readString(source);
            final SymbolTable table = SymbolTable.create(content);
            byte[] bytes = ByteUtils.longToBytes(table.encode(content));
            Files.write(dest, bytes, StandardOpenOption.WRITE);
            if (onDone != null) {
                onDone.accept(dest);
            }
        } catch (IOException e) {
            fail(e);
        }
    }

    private void fail(String reason) {
        throw new CompressionException(reason);
    }

    private void fail(IOException e) {
        throw new CompressionException(e);
    }

    /**
     * Sets a consumer to be notified once the operation has been successfully executed.
     * The consumer will NOT be notified if the operation fails, as this will result in a RuntimeException.
     */
    public void setOnDone(Consumer<Path> onDone) {
        this.onDone = onDone;
    }
}
