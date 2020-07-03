package unitTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import com.kurodev.filecompressor.compress.FileDecompressor;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static unitTests.TestFiles.*;

/**
 * @author kuro
 **/
public class FileDecompressorTest {
    private static final List<Path> deleteOnDone = new ArrayList<>();

    @AfterClass
    public static void deleteTestFiles() throws IOException {
        for (Path path : deleteOnDone) {
            Files.delete(path);
        }
    }

    @Test
    public void findCanGenerateDestinationFile() {
        FileDecompressor comp = new FileDecompressor(TEST_FILE);
        assertEquals("TestCompressionFile.txt", comp.getDestFile().getFileName().toString());
    }

    @Test
    public void compareFileHash() throws IOException {
        FileCompressor compressor = new FileCompressor(TEST_FILE);
        compressor.run();
        deleteOnDone.add(compressor.getDestFile());
        FileDecompressor decompressor = new FileDecompressor(compressor.getDestFile(), TEST_DEST_FILE);
        decompressor.run();
        deleteOnDone.add(decompressor.getDestFile());
        checkIsIdentical(compressor.getSrcFile(), decompressor.getDestFile());
    }

    @Test
    public void compareSmallerFileContent() throws IOException {
        FileCompressor compressor = new FileCompressor(SMALLER_TEST_FILE);
        compressor.run();
        deleteOnDone.add(compressor.getDestFile());
        FileDecompressor decompressor = new FileDecompressor(compressor.getDestFile(), Paths.get("./testfiles/SmallerDecompressed.txt"));
        decompressor.run();
        deleteOnDone.add(decompressor.getDestFile());
        checkIsIdentical(compressor.getSrcFile(), decompressor.getDestFile());

    }

    public void checkIsIdentical(Path original, Path compare) throws IOException {
        List<String> before = Files.readAllLines(original);
        List<String> after = Files.readAllLines(compare);
        assertEquals(before.size(), after.size());
        for (int i = 0; i < before.size(); i++) {
            assertEquals(before.get(i), after.get(i));
        }
    }
}

