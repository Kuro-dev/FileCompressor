package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author kuro
 **/
public class FileCompressor extends FileOperationHandler {
    static final String COMPRESSED_FILE_EXTENSION = ".cmp";

    /**
     * @param source the source file to compress.
     */
    public FileCompressor(Path source) {
        super(source, autogenDestFile(source));
    }

    /**
     * @param source the source file to compress.
     * @param dest   the destination file to generate
     */
    public FileCompressor(Path source, Path dest) {
        super(source, dest);
    }

    private static Path autogenDestFile(Path source) {
        Path parent = source.getParent();
        Path filenameAsPath = source.getFileName();
        String fileName = filenameAsPath.toString();
        return Paths.get(parent.toString() + "/" + fileName + COMPRESSED_FILE_EXTENSION);
    }

    @Override
    protected void work() throws IOException {
        final String content = Files.readString(source);
        final SymbolTable table = TableFactory.create(content);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(table.getTable());
        output.write(table.encode(content));
        if (!Files.exists(dest)) {
            Files.createFile(dest);
        }
        Files.write(dest, output.toByteArray(), StandardOpenOption.WRITE);
    }
}
