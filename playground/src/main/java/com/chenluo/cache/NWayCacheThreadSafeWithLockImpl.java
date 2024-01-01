package com.chenluo.cache;

import com.chenluo.cache.replacepolicy.ReplacePolicy;

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

    public NWayCacheThreadSafeWithLockImpl(int S, int N) {
        this.S = S;
        this.N = N;
        this.entries = new CacheEntry[S];
        for (int i = 0; i < S; i++) {
            // init head
            this.entries[i] = new CacheEntry<K, V>(null, null);
        }
        locks = new ReentrantReadWriteLock[S];
        for (int i = 0; i < S; i++) {
            locks[i] = new ReentrantReadWriteLock();
        }
    }

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

        } catch (Exception e) {
            System.out.println("failed to put" + key + ":" + value);
        } finally {
            lock.writeLock().unlock();
        }

        return success;
    }

    private boolean putEntry(int hash, CacheEntry<K, V> entry) {
        CacheEntry head = entries[hash];

        return false;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        ReadWriteLock lock = locks[hash];
        try {
            lock.readLock().lock();

        } finally {
            lock.readLock().unlock();
        }
        return null;
    }

    @Override
    public void print() {
        System.out.println("------------------");
        for (int i = 0; i < S; i++) {
            System.out.println("");
        }
        System.out.println("------------------");
    }

    private int hash(K key) {
        if (key != null) {
            int result = key.hashCode() % S;
            return result >= 0 ? result : (-result) % S;
        }
        return 0;
    }
}
