package com.chenluo;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserAgent {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(64);
        List<String> posters = List.of("01","02","03","04");
        List<String> followers = List.of("01","02","03","04");
        for (String poster : posters) {
            pool.submit(() -> {
                PosterUserImpl posterUser = new PosterUserImpl(poster);
                posterUser.start();
            });
        }
        for (String follower : followers) {
            pool.submit(() -> {
                FollowerUserImpl followerUser = new FollowerUserImpl(follower);
                followerUser.start();
            });

        }

        for (int i = 0; i < 32; i++) {
            pool.submit(() -> {
                PosterUserImpl posterUser = new PosterUserImpl("01");
                posterUser.start();
            });
        }

        Thread.sleep(100000);
        pool.shutdownNow();

    }
}