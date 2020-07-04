package unitTests.filetests;

import com.kurodev.filecompressor.compress.CompressorFactory;
import com.kurodev.filecompressor.compress.FileCompressor;
import org.junit.Test;

import java.io.IOException;

import static unitTests.filetests.TestFile.TEST_FILE;


/**
 * @author kuro
 **/
public class FileCompressorTest extends FileOperationTest {


    @Test
    public void createCompressedFile() throws InterruptedException, IOException {
        FileCompressor comp = CompressorFactory.compressor(TEST_FILE.original());
        comp.run();
    }
}
