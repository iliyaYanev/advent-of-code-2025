package day_02;

import java.util.regex.Pattern;

public class GiftShop {

    private static final Pattern NEW_RULES_PATTERN = Pattern.compile("^([1-9]\\d*)\\1+$");
    private static final Pattern OLD_RULES_PATTERN = Pattern.compile("^([1-9]\\d*)\\1$");

    public static long invalidIdsSum(String input, boolean newRules) {
        String[] ranges = input.trim().split(",");
        long sum = 0;

        for(String range : ranges) {
            long start = Long.parseLong(range.split("-")[0]);
            long end = Long.parseLong(range.split("-")[1]);

            for (long i = start; i <= end; i++) {
                String strValue = String.valueOf(i);

                if(newRules && NEW_RULES_PATTERN.matcher(strValue).matches()) {
                    sum += i;
                }
                else if (OLD_RULES_PATTERN.matcher(strValue).matches()) {
                    sum += i;
                }
            }
        }

        return sum;
    }
}
