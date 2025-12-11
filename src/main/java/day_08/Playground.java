package day_08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import util.Connection;
import util.Junction;

public class Playground {

    public static long calculateCircuits(List<String> fileContents, boolean isSmall) {
        List<Junction> junctionBoxes = parseJunctionBoxes(fileContents);

        Map<Double, Connection> distances = calculateDistances(junctionBoxes);
        List<Set<Junction>> circuits = calculateCircuits(distances, junctionBoxes);

        sortCircuits(circuits);

        return isSmall ? calculateProduct(circuits) :
            calculateFinalConnection(distances, junctionBoxes);
    }

    private static List<Junction> parseJunctionBoxes(List<String> fileContents) {
        List<Junction> junctions = new ArrayList<>();

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

    private static Map<Double, Connection> calculateDistances(List<Junction> coordinates) {
        // Distances between two junctions.
        TreeMap<Double, Connection> connectionDistances = new TreeMap<>();

        for (Junction x : coordinates) {
            for (Junction y : coordinates) {
                if (!x.equals(y)) {
                    connectionDistances.put(x.getDistance(y), new Connection(x, y));
                }
            }
        }

        return connectionDistances;
    }

    private static List<Set<Junction>> calculateCircuits(Map<Double, Connection> connectionDistances, List<Junction> junctionBoxes) {
        List<Set<Junction>> circuits = junctionBoxes.stream().map(j -> new HashSet<>(Set.of(j)))
            .collect(Collectors.toList());

        int iterations = 0;
        for (Connection connection : connectionDistances.values()) {
            mergeCircuits(circuits, connection);

            iterations++;
            if (iterations == 1000) {
                break;
            }
        }

        return circuits;
    }

    private static void mergeCircuits(List<Set<Junction>> circuits, Connection connection) {
        Junction first = connection.first();
        Junction second = connection.second();

        List<Set<Junction>> combinedCircuits = new ArrayList<>();

        for (Set<Junction> circuit : circuits) {
            if (circuit.contains(first) || circuit.contains(second)) {
                circuit.add(first);
                circuit.add(second);
                combinedCircuits.add(circuit);
            }
        }

        if (combinedCircuits.size() > 1) {
            Set<Junction> total = new HashSet<>();

            for (Set<Junction> circuit : combinedCircuits) {
                circuits.remove(circuit);
                total.addAll(circuit);
            }

            circuits.add(total);
        }
    }

    private static long calculateProduct(List<Set<Junction>> circuits) {
        long total = 1;
        int i = 0;

        for (Set<Junction> circuit : circuits) {
            total *= circuit.size();
            i++;

            if (i == 3) {
                break;
            }
        }

        return total;
    }

    private static void sortCircuits(List<Set<Junction>> circuits) {
        // Sort the circuits by their size.
        circuits.sort((o1, o2) -> o2.size() - o1.size());
    }

    private static long calculateFinalConnection(Map<Double, Connection> connectionDistances, List<Junction> junctionBoxes) {
        List<Set<Junction>> circuits = junctionBoxes.stream().map(jb -> new HashSet<>(Set.of(jb)))
            .collect(Collectors.toList());

        for (Connection connection : connectionDistances.values()) {
            mergeCircuits(circuits, connection);

            if (circuits.size() == 1 && circuits.getFirst().size() == junctionBoxes.size()) {
                return connection.first().x() * connection.second().x();
            }
        }

        throw new IllegalArgumentException("No pair of junction boxes leads to 1 big circuit");
    }
}
