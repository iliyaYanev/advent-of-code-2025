package day_02;

public class GiftShop {

    public static long invalidIdsSum(String input, boolean newRules) {
        String[] ranges = input.trim().split(",");
        long sum = 0;

        for(String range : ranges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            for (long i = start; i <= end; i++) {
                String strValue = String.valueOf(i);
                int len = strValue.length();

                if (newRules) {
                    // For new rules, check if string can be split into 2+ equal repeating parts
                    if (isRepeatingPattern(strValue, len)) {
                        sum += i;
                    }
                } else {
                    // For old rules, check if string is exactly 2 repeating parts
                    if (len % 2 == 0 && isExactlyTwoHalves(strValue, len)) {
                        sum += i;
                    }
                }
            }
        }

        return sum;
    }

    private static boolean isExactlyTwoHalves(String str, int len) {
        int half = len / 2;

        for (int i = 0; i < half; i++) {
            if (str.charAt(i) != str.charAt(i + half)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isRepeatingPattern(String str, int len) {
        // Try all possible pattern lengths that divide evenly and result in minRepeats+ repetitions
        for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
            if (len % patternLen == 0) {
                int repeats = len / patternLen;
                if (repeats >= 2 && matchesPattern(str, patternLen, repeats)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean matchesPattern(String str, int patternLen, int repeats) {
        for (int rep = 1; rep < repeats; rep++) {
            for (int i = 0; i < patternLen; i++) {
                if (str.charAt(i) != str.charAt(rep * patternLen + i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
