package util;

public record Pair(int a, int b) {

    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair(int a1, int b1)) {
            return (a == a1 && b == b1) || (a == b1 && b == a1);
        }

        return false;
    }

}
