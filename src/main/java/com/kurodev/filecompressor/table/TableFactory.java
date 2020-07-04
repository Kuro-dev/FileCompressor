package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.exception.DecompressionException;
import com.kurodev.filecompressor.exception.ErrorCode;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kuro
 **/
public class TableFactory {

    private static List<CharCounter> createCountersFromTable(byte[] tableData) {
        final List<CharCounter> counters = new ArrayList<>();
        for (int i = 0; i < tableData.length; i = i + 2) {
            if (tableData.length > (i + 1)) {
                byte key = tableData[i];
                byte leadingZeros = tableData[i + 1];
                CharCounter counter = new CharCounter(key);
                counter.setLeadingZeros(leadingZeros);
                counters.add(counter);
            }
        }
        return counters;
    }

    private static List<CharCounter> createCountersFromString(InputStream source) throws IOException {
        List<CharCounter> counters = new ArrayList<>();
        int aChar;
        while ((aChar = source.read()) > 0) {
            CharCounter counter = find(counters, (byte) aChar);
            counter.increment();
            if (!counters.contains(counter))
                counters.add(counter);
        }
        return counters;
    }

    private static CharCounter find(List<CharCounter> list, byte character) {
        for (CharCounter charCounter : list) {
            if (charCounter.getCharacter() == character) {
                return charCounter;
            }
        }
        return new CharCounter(character);
    }

    //######################################################################
    //Create from file or String


    public static SymbolTable create(InputStream source) throws IOException {
        List<CharCounter> counters = createCountersFromString(source);
        SymbolTable table = new SymbolTable(counters);
        table.evaluateTable();
        return table;
    }

    public static SymbolTable create(InputStream source, ProgressCallBack callBack, double progressInterval) throws IOException {
        List<CharCounter> counters = createCountersFromString(source);
        SymbolTable table = new ProgressTrackingSymbolTable(counters, callBack, progressInterval);
        table.evaluateTable();
        return table;
    }

    public static SymbolTable create(InputStream source, ProgressCallBack callBack) throws IOException {
        return create(source, callBack, callBack.getInterval());
    }

    public static SymbolTable create(byte[] source) throws IOException {
        return create(new ByteArrayInputStream(source));
    }

    //######################################################################
    //Create from Compressed file

    public static SymbolTable createFromTable(InputStream tableData) throws IOException {
        List<CharCounter> counters = createCountersFromTable(extractTable(tableData));
        return new SymbolTable(counters);
    }

    public static SymbolTable createFromTable(InputStream tableData, ProgressCallBack callBack) throws IOException {
        List<CharCounter> counters = createCountersFromTable(extractTable(tableData));
        return new ProgressTrackingSymbolTable(counters, callBack, callBack.getInterval());
    }

    public static byte[] extractTable(InputStream bytes) throws IOException {
        final byte[] delimiter = SymbolTable.END_OF_TABLE_MARKER;
        final PushbackInputStream in = new PushbackInputStream(bytes, delimiter.length);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean found = false;
        while (peek(in) != -1) {
            byte[] peek = peek(in, delimiter.length);
            if (Arrays.equals(peek, delimiter)) {
                found = true;
                break;
            } else {
                out.write(in.read());
            }
        }
        if (found)
            return out.toByteArray();

        throw new DecompressionException(ErrorCode.TABLE_SIGNATURE_NOT_FOUND);
    }

    private static int peek(PushbackInputStream in) throws IOException {
        int data = in.read();
        in.unread(data);
        return data;
    }

    private static byte[] peek(PushbackInputStream in, int length) throws IOException {
        byte[] result = new byte[length];
        for (int i = 0; i < result.length; i++) {
            int data = in.read();
            result[i] = (byte) data;
        }
        in.unread(result);
        return result;
    }
}
