package com.kurodev.filecompressor.byteutils.reader;

import com.kurodev.filecompressor.compress.CompressorFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kuro
 **/
public class ByteReader {
    private static final int CHECK_BIT = 0b10000000;
    private final InputStream bytes;
    private final int totalBytes;
    int index = 0;
    int current;
    byte turn;
    private boolean hasMore = true;
    private final byte[] buffer;
    int bufferIndex = 0;
    int lastRead = -1;

    public ByteReader(InputStream bytes) throws IOException {
        this(bytes, CompressorFactory.STANDARD_BUF_SIZE);
    }

    public ByteReader(InputStream bytes, int bufSize) throws IOException {
        this.bytes = bytes;
        totalBytes = bytes.available();
        buffer = new byte[bufSize];
        assignNextByte();
    }

    private void assignNextByte() throws IOException {
        if (bufferIndex >= lastRead) {
            lastRead = bytes.read(buffer) - 1;
            bufferIndex = 0;
        } else {
            bufferIndex++;
        }
        current = buffer[bufferIndex];
        turn = 0;
        index++;
    }

    void checkTurn() throws IOException {
        if (turn < 8) {
            return;
        }
        if (index < totalBytes) {
            assignNextByte();
        } else {
            hasMore = false;
        }
    }

    public boolean read() throws IOException {
        checkTurn();
        boolean ret = ((current & CHECK_BIT) == CHECK_BIT);
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
