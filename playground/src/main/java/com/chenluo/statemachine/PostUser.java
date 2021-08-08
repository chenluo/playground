package com.chenluo.statemachine;

import com.google.common.collect.Lists;

public class PostUser extends User {

    public PostUser(String userName) {
        super(userName);
        this.state = "Home";
        transitionMatrix.put("Home", Lists.newArrayList(0.1, 0.1, 0.8));
        transitionMatrix.put("Visit", Lists.newArrayList(0.2, 0.4, 0.4));
        transitionMatrix.put("Post", Lists.newArrayList(0.2, 0.1, 0.7));
    }
}
