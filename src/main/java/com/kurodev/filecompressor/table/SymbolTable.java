package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.byteutils.reader.ByteReader;
import com.kurodev.filecompressor.byteutils.writer.ByteWriter;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author kuro
 * @see TableFactory
 **/
public class SymbolTable {
    public static final byte[] END_OF_TABLE_MARKER = {0x1b, 0x1b, 0x17};

    private final List<CharCounter> characterList;

    /**
     * Use {@link TableFactory} to create object instance.
     */
    SymbolTable(List<CharCounter> characterList) {
        characterList.sort(Comparator.comparingInt(CharCounter::getCount));
        Collections.reverse(characterList);
        this.characterList = characterList;
    }

    public List<CharCounter> getCharacterList() {
        return characterList;
    }

    public void evaluateTable() {
        for (int i = 0; i < characterList.size(); i++) {
            CharCounter charCounter = characterList.get(i);
            charCounter.setLeadingZeros(i);
        }
    }

    public byte[] encode(String chars) {
        return encode(chars.getBytes());
    }

    public byte[] encode(byte[] chars) {
        ByteWriter writer = new ByteWriter();
        for (byte chara : chars) {
            CharCounter character = this.find(chara);
            int zeros = character.getLeadingZeros();
            for (int i = 0; i < zeros; i++) {
                writer.writeZero();
            }
            writer.writeOne();
        }
        writer.fillLastByte();
        return writer.getBytes();
    }

    private CharCounter find(byte aChar) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getCharacter() == aChar) {
                return charCounter;
            }
        }
        throw new RuntimeException("Char missing in table: '" + aChar + "'");
    }

    private byte find(int leadingZeros) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getLeadingZeros() == leadingZeros) {
                return charCounter.getCharacter();
            }
        }
        throw new RuntimeException("code missing in table: '" + leadingZeros + "'");
    }

    //TODO REDO THIS MESS AHHHH. It does not work.
    public String decode(byte[] msg) {
        final StringBuilder builder = new StringBuilder();
        final ByteReader reader = new ByteReader(msg);
        int zeros = 0;
        while (reader.hasMore()) {
            boolean isAOne = reader.read();
            if (isAOne) {
                char character = (char) find(zeros);
                builder.append(character);
                zeros = 0;
            } else {
                zeros++;
            }
        }
        return builder.toString();
    }

    public byte[] getTable() {
        final ByteArrayOutputStream os = new ByteArrayOutputStream(characterList.size() * 2);
        for (CharCounter counter : characterList) {
            byte character = counter.getCharacter();
            int lead = counter.getLeadingZeros();
            os.write(character);
            os.write(lead);
        }
        os.write(END_OF_TABLE_MARKER, 0, END_OF_TABLE_MARKER.length);
        return os.toByteArray();
    }
}
