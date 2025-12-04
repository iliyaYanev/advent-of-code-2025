package util;

import java.util.ArrayList;
import java.util.List;

public class Position {

    private int x;
    private int y;
    private int z;

    private int weight;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Position> allNeighbors() {
        List<Position> neighbors = new ArrayList<>();
        for (int xDelta = -1; xDelta <= 1; xDelta++) {
            for (int yDelta = -1; yDelta <= 1; yDelta++) {
                if (xDelta != 0 || yDelta != 0) {
                    neighbors.add(new Position(x + xDelta, y + yDelta));
                }
            }
        }
        return neighbors;
    }

    public List<Position> diagonalNeighbors() {
        List<Position> neighbors = new ArrayList<>();

        for (int xDelta = -1; xDelta <= 1; xDelta++) {
            for (int yDelta = -1; yDelta <= 1; yDelta++) {
                if (xDelta != 0 && yDelta != 0) {
                    neighbors.add(new Position(x + xDelta, y + yDelta));
                }
            }
        }

        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position that = (Position) obj;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return 10000 * x + 100 * y + z;
    }

    @Override
    public String toString() {
        return (x + " " + y);
    }

    public String augmentedToString() {
        return (x + " " + y + " " + z + " " + weight);
    }
}
