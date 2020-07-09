package unitTests.filetests;

import com.kurodev.filecompressor.compress.CompressorFactory;
import com.kurodev.filecompressor.compress.FileCompressor;
import com.kurodev.filecompressor.compress.FileDecompressor;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

/**
 * @author kuro
 **/
public class FileDecompressorTest extends FileOperationTest {

    @Test
    public void compareFileContentTest() throws IOException {
        for (TestFile file : TestFile.values()) {
            ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            FileCompressor compressor = CompressorFactory.compressor(file.original(), compressed);
            compressor.run();

            ByteArrayOutputStream decompressed = new ByteArrayOutputStream();
            FileDecompressor decompressor = CompressorFactory.decompressor(new ByteArrayInputStream(compressed.toByteArray()), decompressed);
            decompressor.run();
            assertEquals(Files.readString(file.original()), new String(decompressed.toByteArray()));
            System.out.println("----------------D-O-N-E--------------------");
        }
    }
}

