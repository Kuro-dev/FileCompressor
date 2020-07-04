package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
//todo add deserialization Method for Creating from existing file
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

    private static List<CharCounter> createCountersFromString(byte[] source) {
        List<CharCounter> counters = new ArrayList<>();
        for (byte aChar : source) {
            CharCounter counter = find(counters, aChar);
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


    public static SymbolTable create(byte[] source) {
        List<CharCounter> counters = createCountersFromString(source);
        SymbolTable table = new SymbolTable(counters);
        table.evaluateTable();
        return table;
    }

    public static SymbolTable create(byte[] source, ProgressCallBack callBack, double progressInterval) {
        List<CharCounter> counters = createCountersFromString(source);
        SymbolTable table = new ProgressTrackingSymbolTable(counters, callBack, progressInterval);
        table.evaluateTable();
        return table;
    }

    public static SymbolTable create(byte[] source, ProgressCallBack callBack) {
        return create(source, callBack, callBack.getInterval());
    }

    public static SymbolTable create(String source) {
        return create(source.getBytes());
    }

    //######################################################################
    //Create from Compressed file

    public static SymbolTable createFromTable(byte[] tableData) {
        List<CharCounter> counters = createCountersFromTable(tableData);
        return new SymbolTable(counters);
    }

    public static SymbolTable createFromTable(byte[] tableData, ProgressCallBack callback) {
        return createFromTable(tableData, callback, callback.getInterval());
    }

    public static SymbolTable createFromTable(byte[] tableData, ProgressCallBack callBack, double progressInterval) {
        List<CharCounter> counters = createCountersFromTable(tableData);
        return new ProgressTrackingSymbolTable(counters, callBack, progressInterval);
    }
}
