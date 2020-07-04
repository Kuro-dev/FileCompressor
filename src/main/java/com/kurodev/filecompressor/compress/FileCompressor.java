package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author kuro
 **/
public class FileCompressor extends FileOperationHandler {

    private final InputStream tableSource;

    /**
     * @param source the source stream to compress.
     * @param dest   the destination stream to generate new byte stream in
     */
    protected FileCompressor(InputStream source, InputStream tableSource, OutputStream dest) {
        super(source, dest);
        this.tableSource = tableSource;
    }


    @Override
    protected void work() throws IOException {
        SymbolTable table = createSymbolTable(tableSource);
        dest.write(table.getTable());
        table.encode(source, dest);
    }

    protected SymbolTable createSymbolTable(InputStream content) throws IOException {
        if (progressCallback != null) {
            return TableFactory.create(content, progressCallback);
        } else {
            return TableFactory.create(content);
        }
    }
}
