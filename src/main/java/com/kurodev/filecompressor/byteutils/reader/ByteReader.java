package com.kurodev.filecompressor.byteutils.reader;

/**
 * @author kuro
 **/
public class ByteReader {
    private final int checkBit = 0b10000000;
    private final byte[] bytes;
    int index = 0;
    int current;
    byte turn;
    private boolean hasMore = true;

    public ByteReader(byte[] bytes) {
        this.bytes = bytes;
        assignNextByte();
    }

    private void assignNextByte() {
        current = Byte.toUnsignedInt(bytes[index++]);
    }

    void checkTurn() {
        if (turn < 8) {
            return;
        }
        if (index < bytes.length) {
            assignNextByte();
            turn = 0;
        } else {
            hasMore = false;
        }
    }

    public boolean read() {
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
