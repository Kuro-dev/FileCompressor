package unitTests.filetests;

import com.kurodev.filecompressor.compress.CompressorFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author kuro
 **/
public enum TestFile {
    SMALLER_TEST_FILE("SmallerTestFile.txt"),
    MEDIUM_TEST_FILE("MediumTestFile.txt"),
    BIG_TEST_FILE("BigTestFile.txt");

    private final Path original, compressed;

    TestFile(String fileName) {
        original = Paths.get("./testfiles/" + fileName);
        compressed = CompressorFactory.autogenDestFile(original);
    }

    public Path original() {
        return original;
    }

    public Path compressed() {
        return compressed;
    }
}
