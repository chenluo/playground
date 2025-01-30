package com.chenluo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PosterUserImpl extends User {
    public PosterUserImpl(String uid) {
        super(uid);
    }

    @Override
    public void action() {
//        System.out.println("[Poster: %s] start".formatted(uid));
        try {
            post();
//            home();
        } catch (Exception e) {
            System.out.printf("[Poster: %s] %s%n", uid, e);
        }
//        System.out.println("[Poster: %s] end".formatted(uid));
    }


}
