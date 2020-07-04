package unitTests.progressTrackingTests;

import com.kurodev.filecompressor.interfaces.CompressionCallback;
import com.kurodev.filecompressor.interfaces.ProgressCallBack;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author kuro
 **/
public class TestCallback implements ProgressCallBack, CompressionCallback {

    private final double interval;

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
