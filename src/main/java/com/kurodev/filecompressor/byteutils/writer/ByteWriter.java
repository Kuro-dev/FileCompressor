package com.kurodev.filecompressor.byteutils.writer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kuro
 */
public class ByteWriter {

    private final WritableByte current = new WritableByte();
    private final OutputStream out;

    public ByteWriter(OutputStream out) {
        this.out = out;
    }

    private void checkCurrentByte() throws IOException {
        if (!current.hasSpace()) {
            byte aByte = current.getByte();
            out.write(aByte);
            out.flush();
            current.reset();
        }
    }

    public void writeZero() throws IOException {
        checkCurrentByte();
        current.addZero();
    }

    public void writeOne() throws IOException {
        checkCurrentByte();
        current.addOne();
    }

    /**
     * Fills up the currently started byte with zeros and pushes it to the stream.
     */
    public void fillLastByte() throws IOException {
        if (current.getByte() > 0) {
            current.fill();
            out.write(current.getByte());
            current.reset();
        }
    }
}
