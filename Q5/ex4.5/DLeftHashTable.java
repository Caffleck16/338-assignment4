

import java.util.ArrayList;
import java.util.List;

public class DLeftHashTable {
    private final int numBuckets;
    private final List<List<Pair<String, Integer>>> leftTable;
    private final List<List<Pair<String, Integer>>> rightTable;
    private final HashFunction leftHashFunction;
    private final HashFunction rightHashFunction;

    public DLeftHashTable(int numBuckets) {
        this.numBuckets = numBuckets;
        this.leftTable = new ArrayList<>(numBuckets);
        this.rightTable = new ArrayList<>(numBuckets);
        for (int i = 0; i < numBuckets; i++) {
            this.leftTable.add(new ArrayList<>());
            this.rightTable.add(new ArrayList<>());
        }
        this.leftHashFunction = new HashFunction(numBuckets);
        this.rightHashFunction = new HashFunction(numBuckets);
    }

    public void insert(String key, int value) {
        int leftHash = leftHashFunction.hash(key);
        int rightHash = rightHashFunction.hash(key);

        List<Pair<String, Integer>> leftBucket = leftTable.get(leftHash);
        List<Pair<String, Integer>> rightBucket = rightTable.get(rightHash);

        if (leftBucket.size() <= rightBucket.size()) {
            leftBucket.add(new Pair<>(key, value));
        } else {
            rightBucket.add(new Pair<>(key, value));
        }
    }

    public Integer lookup(String key) {
        int leftHash = leftHashFunction.hash(key);
        int rightHash = rightHashFunction.hash(key);

        List<Pair<String, Integer>> leftBucket = leftTable.get(leftHash);
        List<Pair<String, Integer>> rightBucket = rightTable.get(rightHash);

        for (Pair<String, Integer> pair : leftBucket) {
            if (pair.first.equals(key)) {
                return pair.second;
            }
        }

        for (Pair<String, Integer> pair : rightBucket) {
            if (pair.first.equals(key)) {
                return pair.second;
            }
        }

        return null;
    }

    private static class HashFunction {
        private final int numBuckets;

        public HashFunction(int numBuckets) {
            this.numBuckets = numBuckets;
        }

        public int hash(String key) {
            int hash = 0;
            for (char c : key.toCharArray()) {
                hash = (hash * 31 + c) % numBuckets;
            }
            return hash;
        }
    }

    private static class Pair<F, S> {
        public final F first;
        public final S second;

        public Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
