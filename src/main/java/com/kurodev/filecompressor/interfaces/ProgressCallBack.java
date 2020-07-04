package com.kurodev.filecompressor.interfaces;

/**
 * @author kuro
 **/
public interface ProgressCallBack {
    /**
     * Will notify the callback about the current progress of the ongoing compression.
     *
     * @param progress a number between 0 and 1.
     */
    void onProgressChanged(double progress);

    /**
     * returns the desired interval at which the {@link #onProgressChanged(double)} is to be invoked.
     * <p>Example:</p>
     * <p>If the value is set to <code>0.2</code> the callback will be informed every 20% of progress</p>
     *
     * @apiNote Default value = 0.01 (1%)
     */
    default double getInterval() {
        return 0.01;
    }
}
