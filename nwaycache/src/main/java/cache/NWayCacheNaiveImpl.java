package cache;


import cache.replacepolicy.LRUReplacePolicy;
import cache.replacepolicy.LRUReplacePolicyFactory;
import cache.replacepolicy.ReplacePolicy;
import cache.replacepolicy.ReplacePolicyFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NWayCacheNaiveImpl<K, V> implements NWayCache<K, V> {
    private int S; // need upscale?
    private int N; // need upscale?
    private CacheSet<K, V>[] cacheSets; // linked list or array?

    public NWayCacheNaiveImpl(int S, int N, ReplacePolicyFactory<K> replacePolicyFactory) {
        this.S = S;
        this.N = N;
        this.cacheSets = new CacheSet[S];
        for (int i = 0; i < S; i++) {
            // init head
            this.cacheSets[i] = new CacheSet<>(N, replacePolicyFactory.generateReplacePolicy());
        }
    }

    public static void main(String[] args) {
        NWayCache<String, String> nWayCacheImpl = new NWayCacheNaiveImpl<>(5, 20,
                new LRUReplacePolicyFactory<String>(20));
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
            cacheSets[hash].put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to put" + key + ":" + value);
        }

        return success;
    }


    @Override
    public V get(K key) {
        int hash = hash(key);
        return cacheSets[hash].get(key);
    }

    @Override
    public void print() {
        //        System.out.println("------------------");
        for (int i = 0; i < S; i++) {
        }
        //        System.out.println("------------------");
    }

    private int hash(K key) {
        if (key != null) {
            return (N - 1) & key.hashCode();
        }
        return 0;
    }

}
