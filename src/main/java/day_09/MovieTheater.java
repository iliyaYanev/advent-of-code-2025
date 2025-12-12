package day_09;

import java.util.List;
import java.util.stream.IntStream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;

public class MovieTheater {

    private static final GeometryFactory gf = new GeometryFactory();

    public static long largestRectangleArea(List<String> fileContents, boolean isRedGreen) {
        double[][] coords = parseCoordinates(fileContents);

        return isRedGreen ? rectangleAreaContainment(coords) : rectangleAreaNoContainment(coords);
    }

    private static double[][] parseCoordinates(List<String> fileContents) {
        return fileContents.stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(line -> {
                String[] parts = line.split(",");
                return new double[]{
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1])
                };
            })
            .toArray(double[][]::new);
    }

    private static long rectangleArea(double[] c1, double[] c2) {
        long w = Math.abs((long) c1[0] - (long) c2[0]) + 1;
        long h = Math.abs((long) c1[1] - (long) c2[1]) + 1;

        return w * h;
    }

    private static long rectangleAreaNoContainment(double[][] coords) {
        int n = coords.length;

        return IntStream.range(0, n)
            .parallel()
            .boxed()
            .flatMapToLong(i ->
                IntStream.range(i + 1, n)
                    .mapToLong(j -> rectangleArea(coords[i], coords[j]))
            )
            .max()
            .orElse(0);
    }

    private static long rectangleAreaContainment(double[][] coords) {
        int n = coords.length;
        Coordinate[] jtsCoords = new Coordinate[n + 1];

        for (int i = 0; i < n; i++) {
            jtsCoords[i] = new Coordinate(coords[i][0], coords[i][1]);
        }

        jtsCoords[n] = jtsCoords[0];  // Close the polygon

        Polygon poly = gf.createPolygon(jtsCoords);
        PreparedGeometry prepPoly = PreparedGeometryFactory.prepare(poly);
        Envelope envelope = poly.getEnvelopeInternal();

        double envMinX = envelope.getMinX(), envMaxX = envelope.getMaxX();
        double envMinY = envelope.getMinY(), envMaxY = envelope.getMaxY();

        return IntStream.range(0, n)
            .parallel()
            .boxed()
            .flatMapToLong(i ->
                IntStream.range(i + 1, n)
                    .filter(j -> {
                        double[] c1 = coords[i];
                        double[] c2 = coords[j];

                        double xMin = Math.min(c1[0], c2[0]);
                        double xMax = Math.max(c1[0], c2[0]);
                        double yMin = Math.min(c1[1], c2[1]);
                        double yMax = Math.max(c1[1], c2[1]);

                        // Envelope check
                        if (xMin < envMinX || xMax > envMaxX || yMin < envMinY || yMax > envMaxY) {
                            return false;
                        }

                        // Create rectangle geometry and check containment using PreparedGeometry
                        Coordinate[] rectCoords = new Coordinate[]{
                            new Coordinate(xMin, yMin),
                            new Coordinate(xMax, yMin),
                            new Coordinate(xMax, yMax),
                            new Coordinate(xMin, yMax),
                            new Coordinate(xMin, yMin)
                        };

                        Polygon rect = gf.createPolygon(rectCoords);

                        return prepPoly.contains(rect);
                    })
                    .mapToLong(j -> rectangleArea(coords[i], coords[j]))
            )
            .max()
            .orElse(0);
    }
}
