package unitTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static unitTests.TestFiles.TEST_FILE;


/**
 * @author kuro
 **/
public class FileCompressorTest {
    protected static final List<Path> deleteOnDone = new ArrayList<>();

    @AfterClass
    public static void deleteTestFiles() throws IOException {
        for (Path path : deleteOnDone) {
            Files.deleteIfExists(path);
        }
    }

    @Test
    public void findCanGenerateDestinationFile() {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        assertEquals("TestCompressionFile.txt.cmp", comp.getDestFile().getFileName().toString());
    }

    @Test
    public void createCompressedFile() throws InterruptedException {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        comp.run();
        deleteOnDone.add(comp.getDestFile());
    }
}
