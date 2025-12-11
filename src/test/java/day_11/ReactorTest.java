package day_11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class ReactorTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_11/dayElevenInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void reactorPartOneTest() {
        long result = Reactor.pathsOut(FILE_CONTENTS, "you");

        assertEquals(782, result);
    }

    @Test
    public void reactorPartTwoTest() {
        long result = Reactor.pathsOut(FILE_CONTENTS, "svr");

        assertEquals(401398751986160L, result);
    }
}
