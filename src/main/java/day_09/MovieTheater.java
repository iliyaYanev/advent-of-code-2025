package day_09;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

public class MovieTheater {

    private static final GeometryFactory gf = new GeometryFactory();

    public static long largestRectangleArea(List<String> fileContents, boolean isRedGreen) {
        long largestRectangleArea;

        List<Coordinate> coords = parseCoordinates(fileContents);
        Polygon poly = createPolygon(coords);

        largestRectangleArea = isRedGreen ? rectangleAreaContainment(coords, poly) :
        rectangleAreaNoContainment(coords);

        return largestRectangleArea;
    }

    private static List<Coordinate> parseCoordinates(List<String> fileContents) {
        return fileContents.stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(line -> {
                String[] parts = line.split(",");
                return new Coordinate(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1])
                );
            })
            .toList();
    }

    private static Polygon createPolygon(List<Coordinate> coords) {
        // Ensure polygon is closed (last == first)
        Coordinate[] arr = coords.toArray(new Coordinate[0]);

        if (!arr[0].equals2D(arr[arr.length - 1])) {
            Coordinate[] closed = Arrays.copyOf(arr, arr.length + 1);
            closed[closed.length - 1] = arr[0];
            arr = closed;
        }

        return gf.createPolygon(arr);
    }

    private static Stream<Coordinate[]> pairStream(List<Coordinate> coords) {
        // Convert to array for faster access
        Coordinate[] arr = coords.toArray(new Coordinate[0]);
        int n = arr.length;

        return IntStream.range(0, n)
            .parallel() // Use parallel processing for large coordinate sets
            .boxed()
            .flatMap(i ->
                IntStream.range(i + 1, n)
                    .mapToObj(j -> new Coordinate[]{arr[i], arr[j]})
            );
    }

    private static long rectangleArea(Coordinate c1, Coordinate c2) {
        long w = Math.abs((long) c1.x - (long) c2.x) + 1;
        long h = Math.abs((long) c1.y - (long) c2.y) + 1;

        return w * h;
    }

    private static Polygon createRectangle(Coordinate c1, Coordinate c2) {
        double xMin = Math.min(c1.x, c2.x);
        double xMax = Math.max(c1.x, c2.x);
        double yMin = Math.min(c1.y, c2.y);
        double yMax = Math.max(c1.y, c2.y);

        Coordinate[] rect = new Coordinate[] {
            new Coordinate(xMin, yMin),
            new Coordinate(xMax, yMin),
            new Coordinate(xMax, yMax),
            new Coordinate(xMin, yMax),
            new Coordinate(xMin, yMin)
        };

        return gf.createPolygon(rect);
    }

    private static long rectangleAreaNoContainment(List<Coordinate> coords) {
        return pairStream(coords)
            .mapToLong(pair -> rectangleArea(pair[0], pair[1]))
            .max()
            .orElse(0);
    }

    private static long rectangleAreaContainment(List<Coordinate> coords, Polygon poly) {
        return pairStream(coords)
            .filter(pair -> {
                Polygon rect = createRectangle(pair[0], pair[1]);
                return poly.contains(rect);
            })
            .mapToLong(pair -> rectangleArea(pair[0], pair[1]))
            .max()
            .orElse(0);
    }
}
