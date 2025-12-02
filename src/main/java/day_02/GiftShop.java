package day_02;

public class GiftShop {

    public static long invalidIdsSum(String input, boolean newRules) {
        String[] ranges = input.trim().split(",");
        long sum = 0;

        for(String range : ranges) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);

            for (long i = start; i <= end; i++) {
                if(newRules) {
                    if(String.valueOf(i).matches("^([1-9]\\d*)\\1+$")) {
                        sum += i;
                    }
                }
                else {
                    if(String.valueOf(i).matches("^([1-9]\\d*)\\1$")) {
                        sum += i;
                    }
                }
            }
        }

        return sum;
    }
}
