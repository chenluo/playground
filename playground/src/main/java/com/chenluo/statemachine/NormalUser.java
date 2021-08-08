package com.chenluo.statemachine;

import com.google.common.collect.Lists;

public class NormalUser extends User {

    public NormalUser(String userName) {
        super(userName);
        this.state = "Home";
        transitionMatrix.put("Home", Lists.newArrayList(0.1, 0.6, 0.3));
        transitionMatrix.put("Visit", Lists.newArrayList(0.1, 0.6, 0.3));
        transitionMatrix.put("Post", Lists.newArrayList(0.5, 0.4, 0.1));
    }

    @Override
    public void homeAction() {
        super.homeAction();
    }

    @Override
    public void visitAction() {
        super.visitAction();
    }

    @Override
    public void postAction() {
        super.postAction();
    }
}
