package day_12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class ChristmasTreeFarmTest {

    private static final String FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileAsString("src/test/resources/day_12/dayTwelveInput.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void christmasTreeFarmTest() {
        long result = ChristmasTreeFarm.suitableRegions(FILE_CONTENTS);

        assertEquals(454, result);
    }
}
