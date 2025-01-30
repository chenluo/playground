package com.chenluo;

public class FollowerUserImpl extends User {
    public FollowerUserImpl(String uid) {
        super(uid);
    }

    @Override
    public void action() {
//        System.out.println("[Follower: %s] start".formatted(uid));
        try {
            home();
        } catch (Exception e) {
            System.out.printf("[Follower : %s] %s%n", uid, e);
        }
//        System.out.println("[Follower: %s] end".formatted(uid));
    }
}
