package unitTests.filetests.progressTrackingTests;

import com.kurodev.filecompressor.compress.CompressorFactory;
import com.kurodev.filecompressor.compress.FileCompressor;
import unitTests.filetests.FileCompressorTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static unitTests.filetests.TestFile.TEST_FILE;

/**
 * @author kuro
 **/
public class ProgressTrackingTaskWorksTest extends FileCompressorTest {

    @Override
    public void createCompressedFile() throws InterruptedException, IOException {
        TestCallback callback = new TestCallback(0.25);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileCompressor comp = CompressorFactory.compressor(TEST_FILE.original(), output);
        comp.setProgressCallback(callback);
        comp.setResultCallback(callback);
        new Thread(comp).start();
        assertTrue(callback.getLatch().await(2, TimeUnit.SECONDS));
    }
}
