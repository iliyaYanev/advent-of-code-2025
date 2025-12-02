package day_02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import util.GetInputFileContents;

public class GiftShopTest {

    private static final String FILE_CONTENTS;

    static {
        try {
            FILE_CONTENTS = GetInputFileContents.getFileAsString("src/test/resources/day_02/dayTwoInput.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void giftShopPartOneTest() {
        long result = GiftShop.invalidIdsSum(FILE_CONTENTS, false);

        assertEquals(18595663903L, result);
    }

    @Test
    public void giftShopPartTwoTest() {
        long result = GiftShop.invalidIdsSum(FILE_CONTENTS, true);

        assertEquals(19058204438L, result);
    }
}
