package com.chenluo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlowFollowerUserImpl extends User {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public SlowFollowerUserImpl(String uid) {
        super(uid);
    }

    @Override
    public void action() {
        try {
            home();
        } catch (Exception e) {
            logger.error("[Follower : {}] Failed with exception", uid, e);
        }
    }
}
