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
                RotationResult result = calcPositionAndZeroPasses(position, rotation);

                zeroCounter += result.zeroHits();
                position = result.newPosition();
            }
            else {
                position = calcPosition(position, Rotation.getFromLine(line));

                if (position == 0) {
                    zeroCounter++;
                }
            }
        }

        return zeroCounter;
    }

    private static RotationResult calcPositionAndZeroPasses(int currentPosition, Rotation rotation) {
        boolean left = rotation.direction() == Direction.LEFT;
        int dist = rotation.distance();

        if (dist == 0) {
            return new RotationResult(currentPosition, currentPosition == 0 ? 1 : 0);
        }

        long zeros = dist / 100;  // full laps

        int mod = dist % 100;
        int pos = currentPosition;

        for (int i = 0; i < mod; i++) {
            if (left) {
                pos = (pos - 1 + 100) % 100;
            } else {
                pos = addRight(pos, 1);
            }
            if (pos == 0) zeros++;
        }

        return new RotationResult(pos, (int) zeros);
    }

    private static int calcPosition(int currentPosition, Rotation rotation) {
        return switch (rotation.direction()) {
            case LEFT -> addLeft(currentPosition, rotation.distance());
            case RIGHT -> addRight(currentPosition, rotation.distance());
        };
    }

    private static int addLeft(int currentPosition, int distance) {
        int newDistance = currentPosition - distance;
        return ((newDistance % 100) + 100) % 100;
    }

    private static int addRight(int currentPosition, int distance) {
        return (currentPosition + distance) % 100;
    }
}
