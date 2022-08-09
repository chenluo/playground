package cache;


import cache.replacepolicy.LRUReplacePolicy;
import cache.replacepolicy.ReplacePolicy;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class NWayCacheThreadSafeWithLockImpl<K, V> implements NWayCache<K, V> {
    ReadWriteLock[] locks;
    // lock is kind of heavy. Maybe CAS is an improvement point. Unsafe is not available.
    // use AtomicInteger instead. CAS is error-prone
    private int S; // need upscale?
    private int N; // need upscale?
    private CacheEntry<K, V>[] entries; // linked list or array?
    private ReplacePolicy replacePolicy;
    private int term = 0; // handle over-flow.

    public static void main(String[] args) {
        NWayCache<String, String> nWayCacheImpl =
                new NWayCacheThreadSafeWithLockImpl<String, String>(5, 2);
        //        nWayCacheImpl.put("A", "A");
        //        nWayCacheImpl.put("AA", "AA");
        //        System.out.println(nWayCacheImpl.get("A"));
        //        System.out.println(nWayCacheImpl.get("A"+"A"));
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            System.out.println("put " + i);
            nWayCacheImpl.put(String.valueOf(i), String.valueOf(i));
            nWayCacheImpl.print();
            if (i % 5 == 0) {
                System.out.println("get 0");
                nWayCacheImpl.get(String.valueOf(0));
                nWayCacheImpl.print();
            }
        }


    }

    public NWayCacheThreadSafeWithLockImpl(int S, int N) {
        this.S = S;
        this.N = N;
        this.entries = new CacheEntry[S];
        for (int i = 0; i < S; i++) {
            // init head
            this.entries[i] = new CacheEntry<K, V>(null, null, 0);
        }
        locks = new ReentrantReadWriteLock[S];
        for (int i = 0; i < S; i++) {
            locks[i] = new ReentrantReadWriteLock();
        }
        replacePolicy = new LRUReplacePolicy();
    }

    /**
     * @param key:  from wiki, it is memory address, actually.
     * @param value
     * @return
     */
    @Override
    public boolean put(K key, V value) {
        int hash = hash(key);
        ReadWriteLock lock = locks[hash];
        boolean success = false;
        try {
            lock.writeLock().lock();
            CacheEntry<K, V> newEntry = buildEntry(key, value);
            success = putEntry(hash, newEntry);
            if (!success) {
                replacePolicy.dropOne(entries[hash]);
                success = putEntry(hash, newEntry);
            }
        } catch (Exception e) {
            System.out.println("failed to put" + key + ":" + value);
        } finally {
            lock.writeLock().unlock();
        }

        return success;
    }

    private boolean putEntry(int hash, CacheEntry<K, V> entry) {
        CacheEntry head = entries[hash];
        for (int i = 0; i < N; i++) {
            if (head.next == null) {
                head.next = entry;
                return true;
            }
            head = head.next;
        }
        return false;
    }

    private CacheEntry<K, V> buildEntry(K key, V value) {
        term++;
        return new CacheEntry<>(key, value, term);
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        ReadWriteLock lock = locks[hash];
        try {
            lock.readLock().lock();
            CacheEntry<K, V> entry = entries[hash].next;

            for (int i = 0; i < N; i++) {
                if (entry == null) {
                    return null;
                }
                if (entry.key.equals(key)) {
                    return visitEntry(entry).value;
                }
                entry = entry.next;
            }
        } finally {
            lock.readLock().unlock();
        }
        return null;
    }

    @Override
    public void print() {
        System.out.println("------------------");
        for (int i = 0; i < S; i++) {
            CacheEntry<K, V> current = entries[i].next;
            for (int j = 0; j < N; j++) {
                if (current != null) {
                    System.out.print(current + " ");
                    current = current.next;
                } else {
                    System.out.print("null ");
                }
            }
            System.out.println("");
        }
        System.out.println("------------------");
    }

    private CacheEntry<K, V> visitEntry(CacheEntry<K, V> entry) {
        term++;
        entry.term = term;
        return entry;
    }

    private int hash(K key) {
        if (key != null) {
            int result = key.hashCode() % S;
            return result >= 0 ? result : (-result) % S;
        }
        return 0;
    }

}