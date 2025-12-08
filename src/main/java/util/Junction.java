package util;

public record Junction(long x, long y, long z) {

    public double getDistance(Junction other) {
        return Math.sqrt(Math.pow(this.x() - other.x(), 2) + Math.pow(this.y() - other.y(), 2)
            + Math.pow(this.z() - other.z(), 2));
    }
}
