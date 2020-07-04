package com.kurodev.filecompressor.exception;

/**
 * @author kuro
 **/
public enum ErrorCode {
    /**
     * Thrown when the byte signature of the file does not match the expected values.
     * <p>{@link com.kurodev.filecompressor.table.SymbolTable#END_OF_TABLE_MARKER This Array}
     * determines the distinction between the table and the actual message.</p>
     *
     * @see com.kurodev.filecompressor.table.SymbolTable
     */
    FILE_SIGNATURE_MISMATCH,
    TABLE_SIGNATURE_NOT_FOUND
}
