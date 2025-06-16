package com.chenluo;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class UserAgent {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(256);
        List<String> users = IntStream.rangeClosed(1, 4) // 1. Create a stream of ints from 1 to 120
                .mapToObj(n -> String.format("%03d", n)) // 2. Convert each int to a 3-digit padded string
                .toList();// 3. Collect the results into a List
//        List<String> posters = List.of("01","02","03","04");
//        List<String> followers = List.of("01","02","03","04");
        List<String> posters = users;
        List<String> followers = users;



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
//
//        for (int i = 0; i < 32; i++) {
//            pool.submit(() -> {
//                PosterUserImpl posterUser = new PosterUserImpl("01");
//                posterUser.start();
//            });
//        }

        Thread.sleep(10000);
        pool.shutdownNow();

    }
}