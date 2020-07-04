package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.exception.ErrorCode;
import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author kuro
 **/
public class FileDecompressor extends FileOperationHandler {

    public FileDecompressor(Path src) {
        super(src, autogenDestFile(src));
    }

    public FileDecompressor(Path src, Path dest) {
        super(src, dest);
    }

    private static Path autogenDestFile(Path source) {
        Path parent = source.getParent();
        Path filenameAsPath = source.getFileName();
        String fileName = filenameAsPath.toString();
        return Paths.get(parent.toString() + "/" + fileName.replaceFirst(FileCompressor.COMPRESSED_FILE_EXTENSION, ""));
    }

    public Path getDestFile() {
        return dest;
    }

    @Override
    protected void work() throws IOException {
        byte[] data = Files.readAllBytes(source);
        int splitIndex = signaturePresent(data);
        if (splitIndex < 0) {
            fail(ErrorCode.FILE_SIGNATURE_MISMATCH);
        }
        int messageIndex = splitIndex + (SymbolTable.END_OF_TABLE_MARKER.length);
        byte[] tableData = new byte[splitIndex];
        System.arraycopy(data, 0, tableData, 0, splitIndex);
        SymbolTable table = createSymbolTable(tableData);
        byte[] decoded = table.decode(Arrays.copyOfRange(data, messageIndex, data.length));
        writeFile(decoded);
    }

    @Override
    protected SymbolTable createSymbolTable(byte[] content) {
        if (progressCallback != null) {
            return TableFactory.createFromTable(content, progressCallback);
        } else {
            return TableFactory.createFromTable(content);
        }
    }

    int signaturePresent(byte[] table) {
        final byte[] delimiter = SymbolTable.END_OF_TABLE_MARKER;
        if (table.length > delimiter.length)
            for (int i = 0; i < table.length; i++) {
                boolean detected = false;
                for (int delim = 0; delim < delimiter.length; delim++) {
                    if (table.length > i + delim) {
                        if (table[i + delim] == delimiter[delim]) {
                            detected = true;
                        } else {
                            detected = false;
                            break;
                        }
                    }
                }
                if (detected) {
                    return i;
                }
            }
        return -1;
    }
}
