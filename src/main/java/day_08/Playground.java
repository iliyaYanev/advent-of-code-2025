package day_08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import util.Connection;
import util.Junction;
import util.UnionFind;

public class Playground {

    public static long calculateCircuits(List<String> fileContents, boolean isSmall) {
        List<Junction> junctionBoxes = parseJunctionBoxes(fileContents);

        List<Connection> sortedConnections = calculateDistances(junctionBoxes);

        return isSmall ? calculateProduct(sortedConnections, junctionBoxes) :
            calculateFinalConnection(sortedConnections, junctionBoxes);
    }

    private static List<Junction> parseJunctionBoxes(List<String> fileContents) {
        List<Junction> junctions = new ArrayList<>(fileContents.size());

        for (String line : fileContents) {
            String[] splitLine = line.split(",");

            junctions.add(new Junction(
                Long.parseLong(splitLine[0]),
                Long.parseLong(splitLine[1]),
                Long.parseLong(splitLine[2]))
            );
        }

        return junctions;
    }

    private static List<Connection> calculateDistances(List<Junction> coordinates) {
        int n = coordinates.size();
        List<Connection> connections = new ArrayList<>(n * (n - 1) / 2);

        // Only calculate each unique pair once (i, j where i < j)
        for (int i = 0; i < n; i++) {
            Junction x = coordinates.get(i);
            for (int j = i + 1; j < n; j++) {
                Junction y = coordinates.get(j);
                connections.add(new Connection(x, y));
            }
        }

        // Sort by distance (ascending) using cached distanceSquared
        connections.sort(Comparator.comparingDouble(Connection::distanceSquared));

        return connections;
    }

    private static long calculateProduct(List<Connection> sortedConnections, List<Junction> junctionBoxes) {
        UnionFind<Junction> uf = new UnionFind<>(junctionBoxes);

        // Process first 1000 connections (not 1000 successful unions)
        int iterations = Math.min(1000, sortedConnections.size());
        for (int i = 0; i < iterations; i++) {
            Connection connection = sortedConnections.get(i);
            uf.union(connection.first(), connection.second());
        }

        // Get sizes of top 3 components
        int[] componentSizes = uf.getComponentSizes();
        long product = 1;
        for (int i = 0; i < Math.min(3, componentSizes.length); i++) {
            product *= componentSizes[i];
        }
        return product;
    }

    private static long calculateFinalConnection(List<Connection> sortedConnections, List<Junction> junctionBoxes) {
        UnionFind<Junction> uf = new UnionFind<>(junctionBoxes);

        for (Connection connection : sortedConnections) {
            if (uf.union(connection.first(), connection.second())) {
                // Check if all junctions are now connected
                if (uf.getComponentCount() == 1) {
                    return connection.first().x() * connection.second().x();
                }
            }
        }

        throw new IllegalArgumentException("No pair of junction boxes leads to 1 big circuit");
    }
}
