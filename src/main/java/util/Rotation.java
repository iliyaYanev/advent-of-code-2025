package util;

public record Rotation(Direction direction, int distance) {

    public static Rotation getFromLine(String line) {
        String trimmedLine = line.trim();
        Direction dir = switch (trimmedLine.charAt(0)) {
            case 'L' -> Direction.LEFT;
            case 'R' -> Direction.RIGHT;
            default -> throw new IllegalStateException("Unexpected value: " + line.charAt(0));
        };

        return new Rotation(dir, Integer.parseInt(trimmedLine.substring(1)));
    }

    public static RotationResult calcPositionAndZeroPasses(int currentPosition, Rotation rotation) {
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

    public static int calcPosition(int currentPosition, Rotation rotation) {
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

