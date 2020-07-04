package com.kurodev.filecompressor.byteutils.reader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kuro
 **/
public class ByteReader {
    private final int checkBit = 0b10000000;
    private final InputStream bytes;
    private final int totalBytes;
    int index = 0;
    int current;
    byte turn;
    private boolean hasMore = true;

    public ByteReader(InputStream bytes) throws IOException {
        this.bytes = bytes;
        totalBytes = bytes.available();
        assignNextByte();
    }

    private void assignNextByte() throws IOException {
        current = bytes.read();
        index++;
    }

    void checkTurn() throws IOException {
        if (turn < 8) {
            return;
        }
        if (index < totalBytes) {
            assignNextByte();
            turn = 0;
        } else {
            hasMore = false;
        }
    }

    public boolean read() throws IOException {
        checkTurn();
        boolean ret = ((current & checkBit) == checkBit);
        current <<= 1;
        turn++;
        return ret;
    }

    public boolean hasMore() {
        return hasMore;
    }

    @Override
    public String toString() {
        return "ByteReader{" +
                "index=" + index +
                ", current=" + Integer.toBinaryString(current) +
                ", turn=" + turn +
                '}';
    }
}
