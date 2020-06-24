package unitTests;

import com.kurodev.filecompressor.table.SymbolTable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
        SymbolTable table = SymbolTable.create(testSource);
        assertEquals(table.getCharacterList().size(), desiredResult);
    }

    @Test
    @Parameters({
            "tttttttt,8", "dddd4aaaaa5,5"
    })
    public void testCharacterCountCountsRightAmountsOfMostUsedCharacter(String test, int result) {
        SymbolTable table = SymbolTable.create(test);
        assertEquals(table.getCharacterList().get(0).getCount(), result);
    }

    @Test
    public void translatorShouldTranslateCorrectly() {
        String testString = "aaaaabbbbccc";
        int testResult = 0b11111101010101001001001;
        SymbolTable table = SymbolTable.create(testString);
        long compressed = table.translate(testString);
        assertEquals(testResult, compressed);
        assertEquals(testString, table.retranslate(testResult));
    }
}
