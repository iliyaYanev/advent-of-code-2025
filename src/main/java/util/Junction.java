package util;

public record Junction(long x, long y, long z) {

    public double getDistance(Junction other) {
        return Math.sqrt(getDistanceSquared(other));
    }

    public double getDistanceSquared(Junction other) {
        long dx = this.x() - other.x();
        long dy = this.y() - other.y();
        long dz = this.z() - other.z();

        return dx * dx + dy * dy + dz * dz;
    }
}
