package unitTests;

import com.kurodev.filecompressor.FileCompressor;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;


/**
 * @author kuro
 **/
public class FileCompressorTest {
    private static final Path TEST_FILE = Paths.get(System.getProperty("user.dir") + "/testfiles/TestCompressionFile.txt");

    @Test
    public void findCanGenerateDestinationFile() {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        assertEquals("TestCompressionFile.cmp", comp.getDestFile().getFileName().toString());
    }
}
