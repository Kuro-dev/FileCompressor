package unitTests;

import com.kurodev.filecompressor.compress.FileDecompressor;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author kuro
 **/
public class FileDecompressorTest {
    private static final Path TEST_FILE = Paths.get(System.getProperty("user.dir") + "/testfiles/TestCompressionFile.txt.cmp");

    @Test
    public void findCanGenerateDestinationFile() {
        FileDecompressor comp = new FileDecompressor(TEST_FILE);
        comp.run(); //will be removed later as it technically doesnt belong to this test
        assertEquals("TestCompressionFile.txt", comp.getDestFile().getFileName().toString());
    }
}
