package unitTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static unitTests.TestFiles.TEST_FILE;


/**
 * @author kuro
 **/
public class FileCompressorTest {


    @Test
    public void findCanGenerateDestinationFile() {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        assertEquals("TestCompressionFile.txt.cmp", comp.getDestFile().getFileName().toString());
    }

    @Test
    public void createCompressedFile() {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        comp.run();
    }
}
