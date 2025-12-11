package day_11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reactor {

    private static final Map<String, Long> MEMO = new HashMap<>();

    private static final Map<String, Long> MEMO_TRACK = new HashMap<>();

    public static long pathsOut(List<String> fileContents, String start) {
        Map<String, String[]> paths = parsePaths(fileContents);

        MEMO.clear();
        MEMO_TRACK.clear();

        return start.equals("you") ? countPaths(paths, start, "out") :
            trackPaths(paths, start, "out", false, false);
    }

    private static Map<String, String[]> parsePaths(List<String> fileContents) {
        Map<String, String[]> paths = new HashMap<>();
        String[] vals;

        for(String line : fileContents) {
            vals = line.split(": ");
            paths.put(vals[0], vals[1].split(" "));
        }

        return paths;
    }

    private static long countPaths(Map<String, String[]> paths, String node, String goal) {
        long count = 0;
        String key = node + "," + goal;

        if(MEMO.containsKey(key)) {
            return MEMO.get(key);
        }

        if(node.equals(goal)) {
            return 1;
        }

        String[] neighbors = paths.get(node);
        for(String neighbor : neighbors){
            count += countPaths(paths, neighbor, goal);
        }

        MEMO.put(key, count);

        return count;
    }

    private static Long trackPaths(Map<String, String[]> paths, String node, String goal, boolean seen_fft, boolean seen_dac) {
        long count = 0;
        String key = node + "," + goal + "," + seen_fft + "," + seen_dac;

        if(MEMO_TRACK.containsKey(key)) {
            return MEMO_TRACK.get(key);
        }

        if(node.equals(goal)) {
            if(seen_fft && seen_dac) {
                return 1L;
            }

            return 0L;
        }

        String[] neighbors = paths.get(node);
        for(String neighbor : neighbors) {
            count += trackPaths(paths, neighbor, goal,
                seen_fft || neighbor.equals("fft"), seen_dac || neighbor.equals("dac"));
        }

        MEMO_TRACK.put(key, count);

        return count;
    }
}
