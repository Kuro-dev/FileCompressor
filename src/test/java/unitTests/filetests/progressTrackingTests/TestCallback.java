package unitTests.filetests.progressTrackingTests;

import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author kuro
 **/
public class TestCallback implements ProgressCallBack, CompressionCallback {

    private final double interval;
    private final CountDownLatch latch = new CountDownLatch(1);

    public TestCallback(double interval) {
        this.interval = interval;
    }

    @Override
    public void onProgressChanged(double progress) {
        System.out.println("current progress: " + progress);
    }

    @Override
    public void onDone() {
        System.out.println("File compression successful");
        latch.countDown();
    }

    @Override
    public void onFail(IOException e) {
        e.printStackTrace();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public double getInterval() {
        return interval;
    }
}
