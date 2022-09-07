import replacepolicy.LRUReplacePolicy;
import replacepolicy.ReplacePolicy;

public class NWayCacheImpl<K, V> implements NWayCache<K, V> {
    private int S;
    private int N;
    private CacheEntry<K, V>[] entries;
    private ReplacePolicy<K, V> replacePolicy;
    private int term = 0; // handle over-flow.

    public NWayCacheImpl(int S, int N) {
        this.S = S;
        this.N = N;
        this.entries = new CacheEntry[S];
        for (int i = 0; i < S; i++) {
            this.entries[i] = new CacheEntry<K, V>(null,null,0);
        }
        this.replacePolicy = new LRUReplacePolicy();
    }


    public V get(K key) {
        int hash = hash(key);
        CacheEntry<K, V> entry = entries[hash];
        CacheEntry<K, V> head = entry.next;
        for (int i = 0; i < N; i++) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }
    public boolean set(K key, V value) {
        int hash = hash(key);
        CacheEntry<K, V> entry = buildEntry(key, value);
        boolean success = putEntry(hash, entry);
        if (!success) {
            K dropKey = replacePolicy.GetDropKey();
            drop(hash, dropKey);
        }
        return success;
    }

    public void print() {

    }

    private int hash(K key) {
        if (key != null) {
            int result = key.hashCode() % S;
            return result >= 0 ? result:(-result)%S;
        }
        return 0;
    }
    private CacheEntry<K, V> buildEntry(K key, V value) {
        term++;
        return new CacheEntry<>(key, value, term);
    }
    private boolean putEntry(int hash, CacheEntry<K, V> entry) {
        CacheEntry<K, V> dummy = entries[hash];
        CacheEntry<K, V> head = dummy.next;
        for (int i = 0; i < N; i++) {
            if (head.next == null) {
                head.next = entry;
                return true;
            }
        }
        return false;
    }
    private void drop(int hash, K dropkey) {
        CacheEntry<K, V> dummy = entries[hash];
        for (int i = 0; i <= N; i++) {
            if (dummy.next != null) {
                if (dummy.next.key.equals(dropkey)) {
                    dummy.next = dummy.next.next;
                }
            }
        }
    }
}
