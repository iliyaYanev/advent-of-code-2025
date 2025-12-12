package util;

public record Connection(Junction first, Junction second, double distanceSquared) {

    public Connection(Junction first, Junction second) {
        this(first, second, first.getDistanceSquared(second));
    }
}
