package unitTests.progressTrackingTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import unitTests.FileCompressorTest;

import static unitTests.TestFiles.TEST_FILE;

/**
 * @author kuro
 **/
public class ProgressTrackingTaskWorksTest extends FileCompressorTest {

    @Override
    public void createCompressedFile() {
        TestCallback callback = new TestCallback(0.25);
        FileCompressor comp = new FileCompressor(TEST_FILE);
        comp.setProgressCallback(callback);
        comp.setResultCallback(callback);
        comp.run();
    }
}
