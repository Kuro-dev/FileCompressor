package unitTests.filetests;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kuro
 **/
public class FileOperationTest {
    public static final boolean deleteFiles = false;
    protected static final List<Path> deleteOnDone = new ArrayList<>();

    @BeforeClass
    public static void fillList() {
        for (TestFile value : TestFile.values()) {
            deleteOnDone.add(value.compressed());
        }
    }

    @AfterClass
    public static void deleteTestFiles() throws IOException {
        if (deleteFiles)
            for (Path path : deleteOnDone) {
                Files.deleteIfExists(path);
            }
    }
}
