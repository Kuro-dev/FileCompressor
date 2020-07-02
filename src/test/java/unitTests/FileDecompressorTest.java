package unitTests;

import com.kurodev.filecompressor.compress.FileDecompressor;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author kuro
 **/
public class FileDecompressorTest {
    private static final Path TEST_FILE = Paths.get(System.getProperty("user.dir") + "/testfiles/TestCompressionFile.txt.cmp");
    private static final Path TEST_DEST_FILE = Paths.get(System.getProperty("user.dir") + "/testfiles/TestDecompressionFile.txt");

    @Test
    public void findCanGenerateDestinationFile() {
        FileDecompressor comp = new FileDecompressor(TEST_FILE);
        assertEquals("TestCompressionFile.txt", comp.getDestFile().getFileName().toString());
    }

    @Test
    public void compareFileHash() throws IOException {
        FileDecompressor comp = new FileDecompressor(TEST_FILE, TEST_DEST_FILE);
        comp.run();
        byte[] before = Files.readAllBytes(TEST_FILE);
        byte[] after = Files.readAllBytes(TEST_DEST_FILE);
        assertEquals(before, after);
    }
}
