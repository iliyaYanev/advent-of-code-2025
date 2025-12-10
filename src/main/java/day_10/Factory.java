package day_10;


import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Model;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import util.Machine;

public class Factory {

    public static long fewestButtonPresses(List<String> fileContents, boolean joltage) {
        List<Machine> machines = parseInput(fileContents);

        if (joltage) {
            return machines.stream().mapToLong(Factory::joltage).sum();
        }

        return machines.stream().mapToInt(Factory::regular).sum();
    }

    private static List<Machine> parseInput(List<String> fileContents) {
        List<Machine> machines = new ArrayList<>();

        for (String line : fileContents) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(" ");
            String pattern = parts[0].substring(1, parts[0].length() - 1);

            boolean[] targetState = new boolean[pattern.length()];
            for (int i = 0; i < pattern.length(); i++) {
                targetState[i] = pattern.charAt(i) == '#';
            }

            List<Set<Integer>> buttons = new ArrayList<>();
            int idx = 1;
            while (idx < parts.length && parts[idx].startsWith("(")) {
                String content = parts[idx].substring(1, parts[idx].length() - 1);

                Set<Integer> set = Arrays.stream(content.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

                buttons.add(set);
                idx++;
            }

            int[] joltages;
            if (idx < parts.length && parts[idx].startsWith("{")) {
                String content = parts[idx].substring(1, parts[idx].length() - 1);
                joltages = Arrays.stream(content.split(",")).mapToInt(Integer::parseInt).toArray();
            } else {
                joltages = new int[0];
            }

            machines.add(new Machine(targetState, buttons, joltages));
        }

        return machines;
    }

    private static boolean tryFindSolution(Machine machine, int target, int start, int depth, boolean[] state) {
        if (depth == target)
            return Arrays.equals(state, machine.targetState());

        for (int i = start; i <= machine.buttons().size() - (target - depth); i++) {
            toggleLights(machine.buttons().get(i), state);
            if (tryFindSolution(machine, target, i + 1, depth + 1, state))
                return true;
            toggleLights(machine.buttons().get(i), state);
        }
        return false;
    }

    private static void toggleLights(Set<Integer> lights, boolean[] state) {
        for (int idx : lights) {
            if (idx < state.length) {
                state[idx] = !state[idx];
            }
        }
    }

    private static int regular(Machine machine) {
        for (int pressCount = 0; pressCount <= machine.buttons().size(); pressCount++) {
            boolean[] initialState = new boolean[machine.targetState().length];

            if (tryFindSolution(machine, pressCount, 0, 0, initialState))
                return pressCount;
        }

        return 0;
    }

    private static long joltage(Machine machine) {
        try (Context ctx = new Context()) {
            Optimize opt = ctx.mkOptimize();

            // Create integer variables for each button
            IntExpr[] presses = new IntExpr[machine.buttons().size()];
            for (int i = 0; i < presses.length; i++) {
                presses[i] = ctx.mkIntConst("p" + i);
                opt.Add(ctx.mkGe(presses[i], ctx.mkInt(0))); // presses >= 0
            }

            // Add constraints for each light/joltage
            for (int i = 0; i < machine.joltages().length; i++) {
                List<ArithExpr> affectingList = new ArrayList<>();

                for (int j = 0; j < machine.buttons().size(); j++) {
                    if (machine.buttons().get(j).contains(i)) {
                        affectingList.add(presses[j]);
                    }
                }

                if (!affectingList.isEmpty()) {
                    // Convert list to properly typed array
                    ArithExpr[] affectingArray = affectingList.toArray(new ArithExpr[0]);
                    ArithExpr sum;

                    if (affectingArray.length == 1) {
                        sum = affectingArray[0];
                    } else {
                        sum = ctx.mkAdd(affectingArray);
                    }

                    opt.Add(ctx.mkEq(sum, ctx.mkInt(machine.joltages()[i])));
                }
            }

            // Minimize total presses
            ArithExpr totalPresses = presses.length == 1 ? presses[0] : ctx.mkAdd(presses);
            opt.MkMinimize(totalPresses);

            // Solve
            if (opt.Check() == Status.SATISFIABLE) {
                Model model = opt.getModel();
                long result = 0;
                for (IntExpr p : presses) {
                    result += ((IntNum) model.evaluate(p, true)).getInt();
                }
                return result;
            } else {
                return 0; // no solution
            }
        }
    }
}
