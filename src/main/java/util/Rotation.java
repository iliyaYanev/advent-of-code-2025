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
}

