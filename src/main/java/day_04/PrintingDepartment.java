package day_04;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.Position;

public class PrintingDepartment {

    public static long paperRolls(String input, boolean remove) {
        long rollsCount = 0;
        Set<Position> rolls = parseRolls(input);

        while (true) {
            Set<Position> forklift = new HashSet<>();

            for (Position c : rolls) {
                int neighbors = 0;

                for (Position neighbor : c.allNeighbors()) {
                    if (rolls.contains(neighbor)) {
                        neighbors++;
                    }
                }

                if (neighbors < 4) {
                    rollsCount++;
                    forklift.add(c);
                }
            }

            if (forklift.isEmpty() || !remove) {
                break;
            }

            rolls.removeAll(forklift);
        }

        return rollsCount;
    }

    private static Set<Position> parseRolls(String input) {
        Set<Position> rolls = new HashSet<>();
        List<String> rows = Arrays.stream(input.split(System.lineSeparator()))
            .toList();

        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i).split("");

            for (int col = 0; col < row.length; col++) {
                if (row[col].equals("@")) {
                    rolls.add(new Position(i, col));
                }
            }
        }

        return rolls;
    }
}
