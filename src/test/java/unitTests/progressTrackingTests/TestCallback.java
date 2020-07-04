package unitTests.progressTrackingTests;

import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.IOException;
import java.nio.file.Path;
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
    public void onDone(Path path) {
        System.out.println("File compression successful!");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public void onFail(IOException ex) {
        System.out.println("FileCompression Failed");
    }

    @Override
    public double getInterval() {
        return interval;
    }
}
