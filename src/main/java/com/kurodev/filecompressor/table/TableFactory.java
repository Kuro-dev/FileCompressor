package com.kurodev.filecompressor.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
//todo add deserialization Method for Creating from existing file
public class TableFactory {

    public static SymbolTable create(byte[] source) {
        List<CharCounter> counters = new ArrayList<>();
        for (byte aChar : source) {
            CharCounter counter = find(counters, aChar);
            counter.increment();
            if (!counters.contains(counter))
                counters.add(counter);
        }
        SymbolTable table = new SymbolTable(counters);
        table.evaluateTable();
        return table;
    }

    public static SymbolTable create(String source) {
        return create(source.getBytes());
    }

    private static CharCounter find(List<CharCounter> list, byte character) {
        for (CharCounter charCounter : list) {
            if (charCounter.getCharacter() == character) {
                return charCounter;
            }
        }
        return new CharCounter(character);
    }

    //TODO most likely not working as intended.
    public static SymbolTable createFromFile(byte[] tableData) {
        final List<CharCounter> counters = new ArrayList<>();
        for (int i = 0; i < tableData.length; i++) {
            if (tableData.length > (i + 1)) {
                byte key = tableData[i];
                byte leadingZeros = tableData[i + 1];
                CharCounter counter = new CharCounter(key);
                counter.setLeadingZeros(leadingZeros);
                counters.add(counter);
            }
        }
        return new SymbolTable(counters);
    }
}
