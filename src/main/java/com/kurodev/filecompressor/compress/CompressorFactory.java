package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.exception.CompressionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author kuro
 **/
public class CompressorFactory {
    public static final int STANDARD_BUF_SIZE = 1024 * 5;
    public static final String COMPRESSED_FILE_EXTENSION = ".cmp";

    public static Path autogenDestFile(Path source) {
        Path parent = source.getParent();
        String fileName = source.getFileName().toString();
        String newName;
        if (fileName.endsWith(COMPRESSED_FILE_EXTENSION)) {
            newName = fileName.replaceFirst(COMPRESSED_FILE_EXTENSION, "");
        } else {
            newName = fileName + COMPRESSED_FILE_EXTENSION;
        }
        return Paths.get(parent.toString(), newName);
    }

    private static void checkFileAccessibility(Path source) throws CompressionException {
        if (!Files.isRegularFile(source))
            fail("Must be a file");
        if (!Files.isReadable(source))
            fail("Cannot read file");
    }

    private static void fail(String reason) throws CompressionException {
        throw new CompressionException(reason);
    }

    public static FileCompressor compressor(InputStream in, InputStream tableSource, OutputStream out) {
        return new FileCompressor(in, tableSource, out);
    }

    public static FileCompressor compressor(Path src, Path dest) throws IOException {
        checkFileAccessibility(src);
        return compressor(Files.newInputStream(src), Files.newInputStream(src), Files.newOutputStream(dest));
    }

    public static FileCompressor compressor(Path src, OutputStream dest) throws IOException {
        return compressor(Files.newInputStream(src), Files.newInputStream(src), dest);
    }

    public static FileCompressor compressor(Path src) throws IOException {
        return compressor(src, autogenDestFile(src));
    }

    //##############################################################################

    public static FileDecompressor decompressor(InputStream in, OutputStream out) {
        return new FileDecompressor(in, out);
    }

    public static FileDecompressor decompressor(Path src, Path dest) throws IOException {
        checkFileAccessibility(src);
        return decompressor(Files.newInputStream(src), Files.newOutputStream(dest));
    }

    public static FileDecompressor decompressor(Path src) throws IOException {
        return decompressor(src, autogenDestFile(src));
    }
}
