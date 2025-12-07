package day_07;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.Position;

public class Laboratories {

    public static long beamSplit(String input, boolean quantumManifold) {
        long splitCount = 0;

        List<Position> splitters = new ArrayList<>();
        Map<Position, Long> beams = new HashMap<>();
        int row = 0;

        for (String line : input.split(System.lineSeparator())) {
            String[] lineArr = line.split("");

            for (int i = 0; i < line.length(); i++) {
                if (lineArr[i].equals("^")) {
                    splitters.add(new Position(row, i));
                }

                if (lineArr[i].equals("S")) {
                    beams.put(new Position(row, i), 1L);
                }
            }
            row++;
        }

        while (beams.keySet().iterator().next().getX() < row) {
            Map<Position, Long> oldBeams = new HashMap<>(beams);
            beams = new HashMap<>();

            for (Position beam : oldBeams.keySet()) {
                Position nextBeam = new Position(beam.getX() + 1, beam.getY());

                if (splitters.contains(nextBeam)) {
                    splitCount += !quantumManifold ? 1 : oldBeams.get(beam);
                    beams.merge(new Position(beam.getX() + 1, beam.getY() + 1), oldBeams.get(beam), Long::sum);
                    beams.merge(new Position(beam.getX() + 1, beam.getY() - 1), oldBeams.get(beam), Long::sum);
                } else {
                    beams.merge(nextBeam, oldBeams.get(beam), Long::sum);
                }
            }
        }

        if (quantumManifold) {
            splitCount++;
        }

        return splitCount;
    }
}
