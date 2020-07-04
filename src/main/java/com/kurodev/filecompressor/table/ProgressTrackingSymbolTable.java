package com.kurodev.filecompressor.table;

import com.kurodev.filecompressor.byteutils.reader.ByteReader;
import com.kurodev.filecompressor.byteutils.writer.ByteWriter;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    @Override
    public void encode(InputStream in, OutputStream out) throws IOException {
        final int total = in.available();
        int index = 0;
        lastProgress = 0;

        ByteWriter writer = new ByteWriter(out);
        int chara;
        while ((chara = in.read()) != -1) {
            CharCounter character = this.find((byte) chara);
            int zeros = character.getLeadingZeros();
            for (int i = 0; i < zeros; i++) {
                writer.writeZero();
            }
            writer.writeOne();

            double progress = ((double) ((int) ((index / (double) total) * 100.0))) / 100.0;
            index++;
            notifyCallback(progress);
        }
        writer.fillLastByte();
        notifyCallback(1);
    }

    @Override
    public void decode(InputStream in, OutputStream out) throws IOException {
        final int total = in.available();
        int index = 0;
        lastProgress = 0;

        final ByteReader reader = new ByteReader(in);
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

            double progress = ((double) ((int) ((index / (double) total) * 100.0))) / 100.0;
            index++;
            notifyCallback(progress);
        }
    }
}
