public interface NWayCache<K, V> {
    public V get(K key);
    public boolean set(K key, V value);
    public void print();
}
