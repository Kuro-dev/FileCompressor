package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.byteutils.reader.ByteReader;
import com.kurodev.filecompressor.byteutils.writer.ByteWriter;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author kuro
 * @see TableFactory
 **/
public class SymbolTable {
    public static final byte[] END_OF_TABLE_MARKER = {0x17, 0x1b, 0x1b, 0x17};
    private static final Logger logger = Logger.getLogger(SymbolTable.class);
    private final List<CharCounter> characterList;

    /**
     * Use {@link TableFactory} to create object instance.
     */
    protected SymbolTable(List<CharCounter> characterList) {
        characterList.sort(Comparator.comparingInt(CharCounter::getCount));
        Collections.reverse(characterList);
        this.characterList = characterList;
    }

    public List<CharCounter> getCharacterList() {
        return characterList;
    }

    void evaluateTable() {
        for (int i = 0; i < characterList.size(); i++) {
            CharCounter charCounter = characterList.get(i);
            charCounter.setLeadingZeros(i);
        }
    }

    protected CharCounter find(byte aChar) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getCharacter() == aChar) {
                return charCounter;
            }
        }
        throw new RuntimeException("Char missing in table: '" + aChar + "'");
    }

    protected byte find(int leadingZeros) {
        for (CharCounter charCounter : characterList) {
            if (charCounter.getLeadingZeros() == leadingZeros) {
                return charCounter.getCharacter();
            }
        }
        throw new RuntimeException("code missing in table: '" + leadingZeros + "'");
    }

    /**
     * not recommended to use for large input.
     */
    public byte[] encode(String chars) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        encode(new ByteArrayInputStream(chars.getBytes()), out);
        return out.toByteArray();
    }

    public void encode(InputStream in, OutputStream out) throws IOException {
        ByteWriter writer = new ByteWriter(out);
        int chara;
        logger.trace("Encoding stream");
        while ((chara = in.read()) != -1) {
            CharCounter character = this.find((byte) chara);
            int zeros = character.getLeadingZeros();
            for (int i = 0; i < zeros; i++) {
                writer.writeZero();
            }
            writer.writeOne();
        }
        writer.fillLastByte();
        logger.trace("Stream Encoded");
    }

    public void decode(InputStream in, OutputStream out) throws IOException {
        final ByteReader reader = new ByteReader(in);
        logger.trace("Decoding stream");
        int zeros = 0;
        while (reader.hasMore()) {
            boolean isAOne = reader.read();
            if (isAOne) {
                char character = (char) find(zeros);
                out.write(character);
                out.flush();
                zeros = 0;
            } else {
                zeros++;
            }
        }
        logger.trace("Stream Decoded");
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
