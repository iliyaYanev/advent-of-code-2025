package day_04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class PrintingDepartmentTest {

    private static final String FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileAsString("src/test/resources/day_04/dayFourInput.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void printingDepartmentPartOneTest() {
        long result = PrintingDepartment.paperRolls(FILE_CONTENTS, false);

        assertEquals(1480, result);
    }

    @Test
    public void printingDepartmentPartTwoTest() {
        long result = PrintingDepartment.paperRolls(FILE_CONTENTS, true);

        assertEquals(8899, result);
    }
}
