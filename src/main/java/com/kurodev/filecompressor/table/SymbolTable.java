package com.kurodev.filecompressor.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author kuro
 **/
public class SymbolTable {
    private final List<CharCounter> characterList;

    private SymbolTable(List<CharCounter> characterList) {
        characterList.sort(Comparator.comparingInt(CharCounter::getCount));
        Collections.reverse(characterList);
        this.characterList = characterList;
    }

    public static SymbolTable create(String source) {
        return create(source.toCharArray());
    }

    public static SymbolTable create(char[] source) {
        List<CharCounter> counters = new ArrayList<>();
        for (char aChar : source) {
            CharCounter counter = find(counters, aChar);
            counter.increment();
            if (!counters.contains(counter))
                counters.add(counter);
        }
        SymbolTable table = new SymbolTable(counters);
        table.evaluateTable();
        return table;
    }

    private static CharCounter find(List<CharCounter> list, char character) {
        for (CharCounter charCounter : list) {
            if (charCounter.getCharacter() == character) {
                return charCounter;
            }
        }
        return new CharCounter(character);
    }

    public List<CharCounter> getCharacterList() {
        return characterList;
    }

    public void evaluateTable() {
        for (int i = 0; i < characterList.size(); i++) {
            CharCounter charCounter = characterList.get(i);
            charCounter.setLeadingZeros(i + 1);
        }
    }

    public long translate(String chars) {
        return translate(chars.toCharArray());
    }

    public long translate(char[] chars) {
        long result = 1;
        for (char aChar : chars) {
            CharCounter counter = find(aChar);
            int lead = counter.getLeadingZeros();
            result = result << lead;
            result++;
        }
        System.out.println("result: " + result);
        System.out.println(Long.toBinaryString(result));
        return result;
    }

    private CharCounter find(char aChar) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getCharacter() == aChar) {
                return charCounter;
            }
        }
        throw new RuntimeException("Char missing in table: '" + aChar + "'");
    }

    private char find(int leadingZeros) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getLeadingZeros() == leadingZeros) {
                return charCounter.getCharacter();
            }
        }
        throw new RuntimeException("code missing in table: '" + leadingZeros + "'");
    }

    public String retranslate(long msg) {
        StringBuilder result = new StringBuilder();
        long i = msg;
        while (i > 1) {
            int count = 1;
            if ((i & 1) == 1) {
                while (((i = i >> 1) & 1) == 0) {
                    count++;
                }
                char character = find(count);
                result.append(character);
            }
        }
        result.reverse();
        return result.toString();
    }
}
