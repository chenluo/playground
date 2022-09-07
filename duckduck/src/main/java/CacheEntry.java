public class CacheEntry<K, V> {
    public K key;
    public V value;
    public CacheEntry<K, V> next;
    public int term;

    public CacheEntry(K key, V value, int term) {
        this.key = key;
        this.value = value;
        this.term = 0;
        this.next = null;
    }
}
