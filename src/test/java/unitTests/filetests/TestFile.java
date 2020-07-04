package unitTests.filetests;

import com.kurodev.filecompressor.compress.CompressorFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author kuro
 **/
public enum TestFile {
    TEST_FILE(Paths.get("./testfiles/TestCompressionFile.txt")),
    SMALLER_TEST_FILE(Paths.get("./testfiles/SmallerTestFile.txt"));

    private final Path original, compressed;

    TestFile(Path path) {
        original = path;
        compressed = CompressorFactory.autogenDestFile(path);
    }

    public Path original() {
        return original;
    }

    public Path compressed() {
        return compressed;
    }
}
