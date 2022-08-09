package cache;

public interface NWayCache<K, V> { // shall we support generic type?
    boolean put(K key, V value);

    V get(K key);

    void print();
}
