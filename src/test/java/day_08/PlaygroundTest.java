package day_08;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class PlaygroundTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_08/dayEightInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void playgroundPartOneTest() {
        long result = Playground.largestCircuits(FILE_CONTENTS, 1000);

        assertEquals(131580, result);
    }

    @Test
    public void playgroundPartTwoTest() {
        long result = Playground.largestCircuits(FILE_CONTENTS, 1000000);

        assertEquals(6844224, result);
    }
}
