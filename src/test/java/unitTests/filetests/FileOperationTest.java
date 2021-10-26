package unitTests.filetests;

import org.junit.AfterClass;

import java.io.IOException;
import java.nio.file.Files;

/**
 * @author kuro
 **/
public class FileOperationTest {
    public static final boolean deleteFiles = true;

    @AfterClass
    public static void deleteTestFiles() throws IOException {
        if (deleteFiles)
            for (TestFile value : TestFile.values()) {
                Files.deleteIfExists(value.compressed());
            }
    }
}
