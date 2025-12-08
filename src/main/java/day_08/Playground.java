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

        List<Set<Junction>> circuits = calculateCircuits(calculateDistances(junctionBoxes), junctionBoxes);

        sortCircuits(circuits);

        return isSmall ? calculateProduct(circuits) :
            calculateFinalConnection(calculateDistances(junctionBoxes), junctionBoxes);
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
        int iterations = 0;

        // The list of all circuits (with a circuit being defined as a Set of junction
        // boxes), initialized a list of sets of single junction boxes.
        List<Set<Junction>> circuits = junctionBoxes.stream().map(j -> new HashSet<>(Set.of(j)))
            .collect(Collectors.toList());

        for (Connection connection : connectionDistances.values()) {
            Junction first = connection.first();
            Junction second = connection.second();

            List<Set<Junction>> combinedCircuits = new ArrayList<>();

            for (Set<Junction> circuit : circuits) {
                if (circuit.contains(first) || circuit.contains(second)) {
                    circuit.add(first);
                    circuit.add(second);
                    // Add this circuit to the list of combined circuits.
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

            iterations++;
            if (iterations == 1000) {
                break;
            }
        }

        return circuits;
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
            Junction first = connection.first();
            Junction second = connection.second();

            // A list of circuits which were combined as a result of adding the two junction
            // boxes first and second.
            List<Set<Junction>> combinedCircuits = new ArrayList<>();

            for (Set<Junction> circuit : circuits) {
                if (circuit.contains(first) || circuit.contains(second)) {
                    // This circuit contains first or second, so add the other one to it (it's a Set, so
                    // doubling is not a problem).
                    circuit.add(first);
                    circuit.add(second);
                    // Add this circuit to the list of combined circuits.
                    combinedCircuits.add(circuit);
                }
            }

            if (combinedCircuits.size() > 1) {
                // More than one circuits was altered as a result of adding the two junction
                // boxes, so combine those.
                Set<Junction> total = new HashSet<>();
                for (Set<Junction> circuit : combinedCircuits) {
                    circuits.remove(circuit);
                    total.addAll(circuit);
                }

                circuits.add(total);
            }

            if (circuits.size() == 1 && circuits.getFirst().size() == junctionBoxes.size()) {
                // There is only 1 circuits and it contains all junction boxes.
                return first.x() * second.x();
            }
        }

        throw new IllegalArgumentException("No pair of junction boxes leads to 1 big circuit");
    }
}
