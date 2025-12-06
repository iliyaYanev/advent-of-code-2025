package day_06;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrashCompactor {

    public static long problemTotal(List<String> fileContents, boolean rightToLeft) {
        long result = 0;

        // Faster removal and addition
        LinkedList<String> linked = new LinkedList<>(fileContents);
        List<Integer> indices = new ArrayList<>();

        String operatorLine = linked.getLast();
        linked.removeLast();

        for(int i = 0; i < operatorLine.length(); i++){
            if(operatorLine.charAt(i) != ' '){
                indices.add(i);
            }
        }

        indices.add(linked.getFirst().length() + 1);

        for(int i = 0; i < indices.size() - 1; i++){
            int index = indices.get(i);
            result += calculateAdvanced(linked, index, indices.get(i + 1) - index - 1, operatorLine.charAt(index), !rightToLeft);
        }

        return result;
    }

    private static long calculate(List<Long> numbers, char operator) {
        long result = 0;

        if (operator == '+') {
            for (Long i : numbers) {
                result += i;
            }
        } else {
            result = 1;

            for (Long i : numbers) {
                result *= i;
            }
        }

        return result;
    }

    private static long calculateAdvanced(List<String> strings, int index, int length, char operator, boolean rightToLeft) {
        List<Long> numbers = new ArrayList<>();

        if(rightToLeft){
            for(String s: strings){
                numbers.add(Long.parseLong(s.substring(index, index + length).trim()));
            }
        } else {
            for(int i = length - 1; i >= 0; i--){
                StringBuilder tmp = new StringBuilder();

                for(String s: strings){
                    tmp.append(s.substring(index, index + length).charAt(i));
                }

                numbers.add(Long.parseLong(tmp.toString().trim()));
            }
        }

        return calculate(numbers, operator);
    }
}
