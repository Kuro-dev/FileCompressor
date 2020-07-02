package unitTests;

import com.kurodev.filecompressor.table.SymbolTable;
import com.kurodev.filecompressor.table.TableFactory;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void testCharacterCountFindsAllChars(String testSource, int desiredResult) {
        SymbolTable table = TableFactory.create(testSource);
        assertEquals(table.getCharacterList().size(), desiredResult);
    }

    @Test
    @Parameters({
            "tttttttt,8", "dddd4aaaaa5,5"
    })
    public void testCharacterCountCountsRightAmountsOfMostUsedCharacter(String test, int result) {
        SymbolTable table = TableFactory.create(test);
        assertEquals(table.getCharacterList().get(0).getCount(), result);
    }

    @Test
    public void translatorShouldTranslateCorrectly() {
        String testString = "asdateastasf";
        SymbolTable table = TableFactory.create(testString);
        byte[] compressed = table.encode(testString);
        assertEquals(testString, new String(table.decode(compressed)));
    }

    @Test
    @Parameters({
            "TestString",
            "Lorem ipsum dolor sit amet consetetur sadipscing elitr",
            "ASRTOIHASTOANSTATSASTJAStasdtohsdtosz"
    })
    public void compressedByteCodeShouldBeSmaller(String testString) {
        SymbolTable table = TableFactory.create(testString);
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
