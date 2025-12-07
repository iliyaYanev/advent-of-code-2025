package day_07;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class LaboratoriesTest {

    private static final String FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileAsString("src/test/resources/day_07/daySevenInput.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void laboratoriesPartOneTest() {
        long result = Laboratories.beamSplit(FILE_CONTENTS, false);

        assertEquals(1499, result);
    }

    @Test
    public void laboratoriesPartTwoTest() {
        long result = Laboratories.beamSplit(FILE_CONTENTS, true);

        assertEquals(24743903847942L, result);
    }
}
