package com.kurodev.filecompressor.byteutils.writer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuro
 */
public class ByteWriter {

    private final List<Byte> bytes = new ArrayList<>();
    private final WritableByte current = new WritableByte();

    private void checkCurrentByte() {
        if (!current.hasSpace()) {
            byte abyte = current.getByte();
            bytes.add(abyte);
            current.reset();
        }
    }

    public void writeZero() {
        checkCurrentByte();
        current.addZero();
    }

    public void writeOne() {
        checkCurrentByte();
        current.addOne();
    }

    /**
     * Fills up the currently started byte with zeros and pushes it to the stream.
     * Recommended to use before invoking the {@link #getBytes()} method.
     */
    public void fillLastByte() {
        if (current.getByte() > 0) {
            current.fill();
            bytes.add(current.getByte());
            current.reset();
        }
    }

    public byte[] getBytes() {
        byte[] ret = new byte[bytes.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bytes.get(i);
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            int val = Byte.toUnsignedInt(aByte);
            builder.append(Integer.toBinaryString(val));
        }
        int val = Byte.toUnsignedInt(current.getByte());
        builder.append(Integer.toBinaryString(val));
        return builder.toString();
    }
}
