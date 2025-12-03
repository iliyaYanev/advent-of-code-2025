package day_03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class LobbyTest {

    private static final List<String> FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileLines("src/test/resources/day_03/dayThreeInput.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void lobbyPartOneTest() {
        long result = Lobby.totalJoltage(FILE_CONTENTS, 2);

        assertEquals(17412, result);
    }

    @Test
    public void lobbyPartTwoTest() {
        long result = Lobby.totalJoltage(FILE_CONTENTS, 12);

        assertEquals(172681562473501L, result);
    }
}
