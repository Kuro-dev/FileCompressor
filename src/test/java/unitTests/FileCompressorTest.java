package unitTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;


/**
 * @author kuro
 **/
public class FileCompressorTest {
    private static final Path TEST_FILE = Paths.get("./testfiles/TestCompressionFile.txt");

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
