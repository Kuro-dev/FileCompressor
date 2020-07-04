package unitTests.progressTrackingTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import unitTests.FileDecompressorTest;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static unitTests.TestFiles.TEST_FILE;

/**
 * @author kuro
 **/
public class ProgressTrackingTaskWorksTest extends FileDecompressorTest {

    @Override
    public void createCompressedFile() throws InterruptedException {
        TestCallback callback = new TestCallback(0.25);
        FileCompressor comp = new FileCompressor(TEST_FILE);
        comp.setProgressCallback(callback);
        comp.setResultCallback(callback);
        new Thread(comp).start();
        assertTrue(callback.getLatch().await(2, TimeUnit.SECONDS));
        //cleanup
        deleteOnDone.add(comp.getDestFile());
    }
}
