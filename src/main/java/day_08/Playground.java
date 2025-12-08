package day_08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.Box;
import util.Pair;

public class Playground {

    public static long largestCircuits(List<String> fileContents, int pairs) {
        List<Box> boxes = new ArrayList<>();
        Set<Pair> closest = new HashSet<>();
        Map<Integer, Integer> cycles = new HashMap<>();

        int applies = 0;
        int tries = 0;

        for (int i = 0; i < fileContents.size(); i++) {
            String[] line = fileContents.get(i).split(",");

            boxes.add(new Box(
                Integer.parseInt(line[0]),
                Integer.parseInt(line[1]),
                Integer.parseInt(line[2]), i)
            );
        }

        while(tries < pairs) {
            Pair pair = closest(boxes, closest);
            closest.add(pair);

            if(apply(pair, boxes)) {
                applies++;

                if(applies == 999 && pairs > 1000) {
                    return (long) boxes.get(pair.a()).getX() * boxes.get(pair.b()).getX();
                }
            }
            tries++;
        }

        for(Box box: boxes){
            int id = box.getId();

            if(cycles.containsKey(id)) {
                cycles.put(id, cycles.get(id) + 1);
            }
            else {
                cycles.put(id, 1);
            }
        }

        List<Integer> c = new ArrayList<>(cycles.values());

        Collections.sort(c);
        Collections.reverse(c);

        return ((long) c.get(0) * c.get(1) * c.get(2));
    }

    private static boolean apply(Pair pair, List<Box> boxes){
        boolean changed = false;
        int a = boxes.get(pair.a()).getId();
        int b = boxes.get(pair.b()).getId();
        if(a == b){
            return false;
        }
        for(Box box: boxes){
            if(box.getId() == a){
                box.setId(b);
                changed = true;
            }
        }

        return changed;
    }

    private static Pair closest(List<Box> boxes, Set<Pair> closest){
        long distance = Long.MAX_VALUE;
        Pair answer = null;

        for(int i = 0; i < boxes.size(); i++){
            for(int j = i + 1; j < boxes.size(); j++){
                Pair candidate = new Pair(i,j);

                if(closest.contains(candidate)){
                    continue;
                }

                Box box1 = boxes.get(i);
                Box box2 = boxes.get(j);

                long dist = (((long) box1.getX() - box2.getX()) * (box1.getX() - box2.getX())) +
                    (((long) box1.getY() - box2.getY()) * (box1.getY() - box2.getY())) +
                    (((long) box1.getZ() - box2.getZ()) * (box1.getZ() - box2.getZ()));

                if(dist < distance){
                    distance = dist;
                    answer = candidate;
                }
            }
        }

        return answer;
    }
}
