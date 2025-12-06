package day_06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class TrashCompactorTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_06/daySixInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void trashCompactorPartOneTest() {
        long result = TrashCompactor.problemTotal(FILE_CONTENTS, false);

        assertEquals(5733696195703L, result);
    }

    @Test
    public void trashCompactorPartTwoTest() {
        long result = TrashCompactor.problemTotal(FILE_CONTENTS, true);

        assertEquals(10951882745757L, result);
    }
}
