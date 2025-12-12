package day_12;

public class ChristmasTreeFarm {

    public static long suitableRegions(String input) {
        long regionCount = 0;

        String[] parts = input.split("\n\n");
        int[] sizes = getSizes(parts);
        String regions = parts[parts.length - 1];

        for (String region : regions.split("\n")) {
            if (region.isEmpty()) {
                continue;
            }

            String[] regionParts = region.split(": ");
            String[] dimensions = regionParts[0].split("x");
            String[] counts = regionParts[1].split(" ");

            int R = Integer.parseInt(dimensions[0]);
            int C = Integer.parseInt(dimensions[1]);

            int totalGridSize = R * C;
            int totalPresentSize = 0;

            for (int i = 0; i < counts.length; i++) {
                int n = Integer.parseInt(counts[i]);
                totalPresentSize += n * sizes[i];
            }


            if (totalPresentSize * 13 < totalGridSize * 10) {
                regionCount++;
            }
        }

        return regionCount;
    }

    private static int[] getSizes(String[] parts) {
        int[] sizes = new int[parts.length - 1];

        for (int i = 0; i < parts.length - 1; i++) {
            String[] lines = parts[i].split("\n");
            int name = Integer.parseInt(lines[0].substring(0, lines[0].length() - 1));
            int size = 0;

            for (int j = 1; j < lines.length; j++) {
                String line = lines[j];

                for (int k = 0; k < line.length(); k++) {
                    if (line.charAt(k) == '#') {
                        size++;
                    }
                }
            }

            sizes[name] = size;
        }

        return sizes;
    }
}
