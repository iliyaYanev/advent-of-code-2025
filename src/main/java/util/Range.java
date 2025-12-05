package util;

public class Range implements Comparable<Range> {

    private final long low;
    private long high;

    public Range(long l, long h) {
        low = l;
        high = h;
    }

    public boolean contains(long l) {
        return l >= low && l <= high;
    }

    public long getLow() {
        return low;
    }

    public long getHigh() {
        return high;
    }

    public void setHigh(long high) {
        this.high = high;
    }

    @Override
    public int compareTo(Range o) {
        return Long.compare(low, o.low);
    }

    public String toString() {
        return low + " " + high;
    }
}
