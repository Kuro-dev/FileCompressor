package unitTests;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author kuro
 **/
@RunWith(JUnitParamsRunner.class)
public class CharacterCountTest {
    @Test
    @Parameters({"test,3",
            "tt,1",
            "tT,2"})
    public void testCharacterCountFindsAllChars(String testString, int desiredResult) throws IOException {
        SymbolTable table = TableFactory.create(new ByteArrayInputStream(testString.getBytes()));
        assertEquals(table.getCharacterList().size(), desiredResult);
    }

    @Test
    @Parameters({
            "tttttttt,8", "dddd4aaaaa5,5"
    })
    public void testCharacterCountCountsRightAmountsOfMostUsedCharacter(String testSource, int result) throws IOException {
        SymbolTable table = TableFactory.create(new ByteArrayInputStream(testSource.getBytes()));
        assertEquals(table.getCharacterList().get(0).getCount(), result);
    }

    @Test
    public void translatorShouldTranslateCorrectly() throws IOException {
        String testString = "asdateastasf";
        SymbolTable table = TableFactory.create(new ByteArrayInputStream(testString.getBytes()));
        byte[] compressed = table.encode(testString);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        table.decode(new ByteArrayInputStream(compressed), bos);
        byte[] decoded = bos.toByteArray();
        assertEquals(testString, new String(decoded));
    }

    @Test
    @Parameters({
            "TestString",
            "Lorem ipsum dolor sit amet consetetur sadipscing elitr",
            "ASRTOIHASTOANSTATSASTJAStasdtohsdtosz",
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    })
    public void compressedByteCodeShouldBeSmaller(String testString) throws IOException {
        SymbolTable table = TableFactory.create(new ByteArrayInputStream(testString.getBytes()));
        byte[] compressed = table.encode(testString);
        assertTrue(binaryString(compressed).length() < binaryString(testString.getBytes()).length());
    }

    private String binaryString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            int val = Byte.toUnsignedInt(aByte);
            builder.append(Integer.toBinaryString(val));
        }
        return builder.toString();
    }
}
