package day_10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class FactoryTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_10/dayTenInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void factoryPartOneTest() {
        long result = Factory.fewestButtonPresses(FILE_CONTENTS, false);

        assertEquals(432, result);
    }

    @Test
    public void factoryPartTwoTest() {
        long result = Factory.fewestButtonPresses(FILE_CONTENTS, true);

        assertEquals(18011, result);
    }
}
