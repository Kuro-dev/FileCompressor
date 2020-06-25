package unitTests;

import com.kurodev.filecompressor.compress.FileCompressor;
import com.kurodev.filecompressor.table.SymbolTable;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


/**
 * @author kuro
 **/
public class FileCompressorTest {
    private static final Path TEST_FILE = Paths.get(System.getProperty("user.dir") + "/testfiles/TestCompressionFile.txt");

    @Test
    public void findCanGenerateDestinationFile() {
        FileCompressor comp = new FileCompressor(TEST_FILE);
        assertEquals("TestCompressionFile.txt.cmp", comp.getDestFile().getFileName().toString());
    }

    @Test
    public void createCompressedFile() throws IOException {
        final String content = Files.readString(TEST_FILE);
        final SymbolTable table = SymbolTable.create(content);
        long coded = table.encode(content);
        System.out.println("Result: " + coded);
        System.out.println("Result: " + table.decode(coded));
    }
}
