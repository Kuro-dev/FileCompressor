package com.kurodev.filecompressor.util;

import java.nio.ByteBuffer;

/**
 * @author kuro
 **/
public class ByteUtils {
    private static final ByteBuffer BYTE_BUFFER = ByteBuffer.allocate(Long.BYTES);

    public static byte[] longToBytes(long x) {
        BYTE_BUFFER.putLong(0, x);
        return BYTE_BUFFER.array();
    }

    public static long bytesToLong(byte[] bytes) {
        BYTE_BUFFER.put(bytes, 0, bytes.length);
        BYTE_BUFFER.flip();//need flip
        return BYTE_BUFFER.getLong();
    }
}
