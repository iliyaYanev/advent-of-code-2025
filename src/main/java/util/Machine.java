package util;

import java.util.List;
import java.util.Set;

public record Machine(
    boolean[] targetState,
    List<Set<Integer>> buttons,
    int[] joltages
) { }
