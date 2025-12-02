package day_01;

import java.util.List;
import util.Direction;
import util.Rotation;
import util.RotationResult;

public class SecretEntrance {

    public static long getPassword(List<String> fileContents, boolean newProtocol) {
        int zeroCounter = 0;
        int position = 50;

        for (String line : fileContents) {
            if (newProtocol) {
                Rotation rotation = Rotation.getFromLine(line);
                RotationResult result = Rotation.calcPositionAndZeroPasses(position, rotation);

                zeroCounter += result.zeroHits();
                position = result.newPosition();
            }
            else {
                position = Rotation.calcPosition(position, Rotation.getFromLine(line));

                if (position == 0) {
                    zeroCounter++;
                }
            }
        }

        return zeroCounter;
    }
}
