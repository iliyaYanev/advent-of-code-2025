package day_05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class CafeteriaTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_05/dayFiveInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void cafeteriaPartOneTest() {
        long result = Cafeteria.freshIngredients(FILE_CONTENTS, false);

        assertEquals(617, result);
    }

    @Test
    public void cafeteriaPartTwoTest() {
        long result = Cafeteria.freshIngredients(FILE_CONTENTS, true);

        assertEquals(338258295736104L, result);
    }
}
