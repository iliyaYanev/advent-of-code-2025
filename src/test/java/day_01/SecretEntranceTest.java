package day_01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class SecretEntranceTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_01/dayOneInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void secretEntrancePartOneTest() {
        long result = SecretEntrance.getPassword(FILE_CONTENTS, false);

        assertEquals(1089, result);
    }

    @Test
    public void secretEntrancePartTwoTest() {
        long result = SecretEntrance.getPassword(FILE_CONTENTS, true);

        assertEquals(6530, result);
    }
}
