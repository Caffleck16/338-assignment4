import java.util.BitSet;
import java.util.Random;

public class BloomFilter {

    private final int bitSetSize;
    private final int refreshCount;
    private final BitSet bitSet;
    private final Random random = new Random();
    private final int[] hashSeeds;

    private int count = 0;

    public BloomFilter(int bitSetSize, int refreshCount) {
        this.bitSetSize = bitSetSize;
        this.refreshCount = refreshCount;
        this.bitSet = new BitSet(bitSetSize);
        this.hashSeeds = new int[]{31, 61, 127}; // Use three hash functions with these seeds
    }

    public void record(String s) {
        int[] hashes = new int[hashSeeds.length];
        for (int i = 0; i < hashSeeds.length; i++) {
            hashes[i] = hash(s, hashSeeds[i]);
            bitSet.set(hashes[i]);
        }
        count++;
        if (count % refreshCount == 0) {
            bitSet.clear();
            count = 0;
            System.out.println("Refresh");
        }
    }

    public boolean lookup(String s) {
        for (int i = 0; i < hashSeeds.length; i++) {
            if (!bitSet.get(hash(s, hashSeeds[i]))) {
                return false;
            }
        }
        return true;
    }

    private int hash(String s, int seed) {
        int hash = seed;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31 + s.charAt(i)) % bitSetSize;
        }
        return hash;
    }

    public static void main(String[] args) {
        // create a BloomFilter with a bitset of size 1000 and refresh every 10 insertions
        BloomFilter filter = new BloomFilter(1000, 10);
    
        // record some objects into the filter
        filter.record("hello");
        filter.record("world");
        filter.record("foo");
        filter.record("bar");
    
        // check if some objects exist in the filter
        System.out.println(filter.lookup("hello")); // true
        System.out.println(filter.lookup("world")); // true
        System.out.println(filter.lookup("foo")); // true
        System.out.println(filter.lookup("bar")); // true
        System.out.println(filter.lookup("baz")); // false
        System.out.println(filter.lookup("qux")); // false
    }
    
}
