package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.byteutils.reader.ByteReader;
import com.kurodev.filecompressor.byteutils.writer.ByteWriter;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author kuro
 **/
public class ProgressTrackingSymbolTable extends SymbolTable {
    private final ProgressCallBack callBack;
    private final double interval;
    double lastProgress = 0;


    /**
     * Use {@link TableFactory} to create object instance.
     *
     * @param interval The interval in percent at which the callback will be notified at. Default = 0.
     *                 <p>if this value is set above 1, the callback will never be notified.</p>
     */
    protected ProgressTrackingSymbolTable(List<CharCounter> characterList, ProgressCallBack callBack, double interval) {
        super(characterList);
        this.callBack = callBack;
        this.interval = interval;
    }

    private void notifyCallback(double progress) {
        if (progress >= this.lastProgress + interval || progress >= 1) {
            callBack.onProgressChanged(progress);
            lastProgress = progress;
        }
    }

    public byte[] encode(byte[] bytes) {
        lastProgress = 0;
        ByteWriter writer = new ByteWriter();
        for (int index = 0; index < bytes.length; index++) {
            byte chara = bytes[index];
            CharCounter character = this.find(chara);
            int zeros = character.getLeadingZeros();
            for (int i = 0; i < zeros; i++) {
                writer.writeZero();
            }
            writer.writeOne();
            double progress = ((double) ((int) ((index / (double) bytes.length) * 100.0))) / 100.0;
            notifyCallback(progress);
        }
        writer.fillLastByte();
        notifyCallback(1);
        return writer.getBytes();
    }

    public byte[] decode(byte[] bytes) {
        lastProgress = 0;
        int index = 0;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        final ByteReader reader = new ByteReader(bytes);
        int zeros = 0;
        while (reader.hasMore()) {
            boolean isAOne = reader.read();
            if (isAOne) {
                char character = (char) find(zeros);
                os.write(character);
                zeros = 0;
            } else {
                zeros++;
            }
            double progress = ((double) ((int) ((index / (double) bytes.length) * 100.0))) / 100.0;
            notifyCallback(progress);
            index++;
        }
        return os.toByteArray();
    }
}
