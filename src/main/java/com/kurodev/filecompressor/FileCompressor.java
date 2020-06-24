package com.kurodev.filecompressor;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author kuro
 **/
public class FileCompressor {
    private static final String COMPRESSED_FILE_EXTENSION = ".cmp";
    private final Path source;
    private final Path dest;

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
        String fileName = filenameAsPath.toString().split("\\.")[0];
        return Paths.get(parent.toString() + "/" + fileName + COMPRESSED_FILE_EXTENSION);
    }

    public Path getDestFile() {
        return dest;
    }

    public void compress() {

    }
}
