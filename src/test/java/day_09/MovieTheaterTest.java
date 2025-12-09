package day_09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class MovieTheaterTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_09/dayNineInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void movieTheaterPartOneTest() {
        long result = MovieTheater.largestRectangleArea(FILE_CONTENTS, false);

        assertEquals(4733727792L, result);
    }

    @Test
    public void movieTheaterPartTwoTest() {
        long result = MovieTheater.largestRectangleArea(FILE_CONTENTS, true);

        assertEquals(1566346198L, result);
    }
}
