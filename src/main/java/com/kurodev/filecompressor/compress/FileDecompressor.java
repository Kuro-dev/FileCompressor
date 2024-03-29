package com.kurodev.filecompressor.compress;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author kuro
 **/
public class FileDecompressor extends FileOperationHandler {


    private SymbolTable table;

    protected FileDecompressor(InputStream src, OutputStream dest) {
        super(src, dest);
    }

    @Override
    protected void prepare() throws Exception {
        table = createSymbolTable(source);
    }

    @Override
    protected void work() throws IOException {
        table.decode(source, dest);
    }

    @Override
    protected SymbolTable createSymbolTable(InputStream content) throws IOException {
        if (progressCallback != null) {
            return TableFactory.createFromTable(content, progressCallback);
        } else {
            return TableFactory.createFromTable(content);
        }
    }

}
