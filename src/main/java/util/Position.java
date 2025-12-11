package util;

import java.util.ArrayList;
import java.util.List;

public class Position {

    private static final int[][] ALL_NEIGHBOR_OFFSETS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Position> allNeighbors() {
        List<Position> neighbors = new ArrayList<>(8);

        for (int[] offset : ALL_NEIGHBOR_OFFSETS) {
            neighbors.add(new Position(x + offset[0], y + offset[1]));
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
}
