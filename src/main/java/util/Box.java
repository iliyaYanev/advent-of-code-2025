package util;

public class Box {

    private final int x;
    private final int y;
    private final int z;

    private int id;

    public Box(int x, int y, int z, int id){
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
