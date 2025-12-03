package day_03;

import java.util.Arrays;
import java.util.List;

public class Lobby {

    public static long totalJoltage(List<String> fileContents, int batteries) {
        long totalJoltage = 0;

        for (String line : fileContents) {
            List<Long> battery = Arrays.stream(line.split(""))
                .map(Long::parseLong)
                .toList();

            totalJoltage += findLargest(battery, batteries);
        }

        return totalJoltage;
    }

    private static int maxIndex(List<Long> nums, int firstBound, int secondBound){
        long largest = 0;
        int index = 0;

        for(int i = firstBound; i <= nums.size() - secondBound; i++){
            if (nums.get(i) > largest){
                largest = nums.get(i);
                index = i;
            }
        }

        return index;
    }

    private static long findLargest(List<Long> nums, int numDigits){
        StringBuilder s = new StringBuilder();
        int currIndex = 0;

        for(int i = numDigits; i > 0; i--){
            currIndex = maxIndex(nums, currIndex, i) + 1;
            s.append(nums.get(currIndex - 1));
        }

        return Long.parseLong(s.toString());
    }
}
