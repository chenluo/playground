package com.chenluo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FollowerUserImpl extends User {
    private final Logger logger = LoggerFactory.getLogger(FollowerUserImpl.class);
    public FollowerUserImpl(String uid) {
        super(uid);
    }

    @Override
    public void action() {
        try {
            homeCache();
        } catch (Exception e) {
            logger.error("[Follower : {}] failed with exception", uid, e);
        }
    }
}
