package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Union-Find (Disjoint Set Union) data structure with path compression and union by rank
 *
 * @param <T> the type of elements in the disjoint sets
 */
public class UnionFind<T> {
    private final Map<T, T> parent;

    private final Map<T, Integer> rank;

    private final Map<T, Integer> size;

    private int componentCount;

    public UnionFind(List<T> elements) {
        parent = new HashMap<>(elements.size());
        rank = new HashMap<>(elements.size());
        size = new HashMap<>(elements.size());
        componentCount = elements.size();

        for (T element : elements) {
            parent.put(element, element);
            rank.put(element, 0);
            size.put(element, 1);
        }
    }

    public T find(T element) {
        T p = parent.get(element);
        if (!p.equals(element)) {
            parent.put(element, find(p)); // Path compression
        }
        return parent.get(element);
    }

    public boolean union(T x, T y) {
        T rootX = find(x);
        T rootY = find(y);

        if (rootX.equals(rootY)) {
            return false; // Already in same set
        }

        // Union by rank
        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);

        if (rankX < rankY) {
            parent.put(rootX, rootY);
            size.put(rootY, size.get(rootY) + size.get(rootX));
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
            size.put(rootX, size.get(rootX) + size.get(rootY));
        } else {
            parent.put(rootY, rootX);
            size.put(rootX, size.get(rootX) + size.get(rootY));
            rank.put(rootX, rankX + 1);
        }

        componentCount--;

        return true;
    }

    public int getComponentCount() {
        return componentCount;
    }

    public int[] getComponentSizes() {
        Map<T, Integer> rootSizes = new HashMap<>();

        for (T element : parent.keySet()) {
            T root = find(element);
            rootSizes.put(root, size.get(root));
        }

        return rootSizes.values().stream()
            .sorted((a, b) -> b - a)
            .mapToInt(Integer::intValue)
            .toArray();
    }
}
