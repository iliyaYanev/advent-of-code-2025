package day_05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.Range;

public class Cafeteria {

    private static int intervalIndex;

    public static long freshIngredients(List<String> fileContents, boolean withRanges) {
        long freshIngredientCount = 0;

        List<Range> ranges = parseRanges(fileContents);
        Collections.sort(ranges);

        if(withRanges) {
            Range currRange = ranges.getFirst();

            for(Range r : ranges) {
                if(r.getLow() > currRange.getHigh()){
                    freshIngredientCount += currRange.getHigh() - currRange.getLow() + 1;
                    currRange = r;
                }
                else {
                    currRange.setHigh(Math.max(currRange.getHigh(), r.getHigh()));
                }
            }

            freshIngredientCount += currRange.getHigh() - currRange.getLow() + 1;
        }
        else {
            for (int i = intervalIndex; i < fileContents.size(); i++) {
                long line = Long.parseLong(fileContents.get(i));

                for(Range range: ranges){
                    if(range.contains(line)){
                        freshIngredientCount++;
                        break;
                    }
                }
            }
        }

        return freshIngredientCount;
    }

    private static List<Range> parseRanges(List<String> fileContents) {
        List<Range> ranges = new ArrayList<>();

        for (int i = 0; i < fileContents.size(); i++) {
            String[] range = fileContents.get(i).split("-");

            if (fileContents.get(i).isEmpty()) {
                intervalIndex = i + 1;
                break;
            }

            ranges.add(new Range(Long.parseLong(range[0]), Long.parseLong(range[1])));
        }

        return ranges;
    }
}
