package com.chenluo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PosterUserImpl extends User {
    private final Logger logger = LoggerFactory.getLogger(PosterUserImpl.class);
    public PosterUserImpl(String uid) {
        super(uid);
    }

    @Override
    public void action() {
        try {
            post();
        } catch (Exception e) {
            logger.error("[Poster: {}] failed with exception", uid, e);
        }
    }


}
