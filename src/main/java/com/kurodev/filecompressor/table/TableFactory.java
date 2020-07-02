package com.kurodev.filecompressor.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
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
}
