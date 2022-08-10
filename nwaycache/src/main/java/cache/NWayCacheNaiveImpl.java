package cache;


import cache.replacepolicy.LRUReplacePolicy;
import cache.replacepolicy.ReplacePolicy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NWayCacheNaiveImpl<K, V> implements NWayCache<K, V> {
    private int S; // need upscale?
    private int N; // need upscale?
    private CacheEntry<K, V>[] entries; // linked list or array?
    private ReplacePolicy replacePolicy;
    private int term = 0; // handle over-flow.

    public NWayCacheNaiveImpl(int S, int N) {
        this.S = S;
        this.N = N;
        this.entries = new CacheEntry[S];
        for (int i = 0; i < S; i++) {
            // init head
            this.entries[i] = new CacheEntry<K, V>(null, null, 0);
        }
        replacePolicy = new LRUReplacePolicy();
    }

    public static void main(String[] args) {
        NWayCache<String, String> nWayCacheImpl = new NWayCacheThreadSafeWithLockImpl<>(5, 20);
        //        nWayCacheImpl.put("A", "A");
        //        nWayCacheImpl.put("AA", "AA");
        //        System.out.println(nWayCacheImpl.get("A"));
        //        System.out.println(nWayCacheImpl.get("A"+"A"));
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(64, 64, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100000), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 200000000; i++) {
            //            System.out.println("put " + i);
            int finalI = i;
            if (finalI % 1000000 == 0) {
                System.out.println(finalI);
            }
            threadPoolExecutor.execute(() -> {
                nWayCacheImpl.put(String.valueOf(finalI), String.valueOf(finalI));
                //                nWayCacheImpl.print();
//                if (finalI % 5 == 0) {
//                    //                    System.out.println("get 0");
//                    nWayCacheImpl.get(String.valueOf(0));
//                    //                    nWayCacheImpl.print();
//                }
            });
        }
        threadPoolExecutor.shutdown();
        while (true) {
            System.out.println("wait for shutdown");
            try {
                if (threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
                threadPoolExecutor.shutdownNow();
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
        boolean success = false;
        try {
            CacheEntry<K, V> newEntry = buildEntry(key, value);
            success = putEntry(hash, newEntry);
            if (!success) {
                Thread.yield(); // for test multi-thread issue
                replacePolicy.dropOne(entries[hash]);
                success = putEntry(hash, newEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to put" + key + ":" + value);
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
        return null;
    }

    @Override
    public void print() {
        //        System.out.println("------------------");
        for (int i = 0; i < S; i++) {
            CacheEntry<K, V> current = entries[i].next;
            for (int j = 0; j < N; j++) {
                if (current != null) {
                    //                    System.out.print(current + " ");
                    current = current.next;
                } else {
                    //                    System.out.print("null ");
                }
            }
            //            System.out.println("");
        }
        //        System.out.println("------------------");
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
