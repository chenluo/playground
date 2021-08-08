package com.chenluo.statemachine;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class User {
    /**
     * Home
     * Visit
     * Post
     */
    protected String state;

    protected Map<String, List<Double>> transitionMatrix = Maps.newHashMap();

    protected final String userName;

    private final Random random = new Random();

    private Logger logger = LoggerFactory.getLogger(User.class);

    protected User(String userName) {
        this.userName = userName;
    }

    public boolean doAction() {
        switch (state) {
            case "Home":
                homeAction();
                break;
            case "Visit":
                visitAction();
                break;
            case "Post":
                postAction();
                break;
            default:

        }
        transit();
        return true;
    }

    private void transit() {
        double p = random.nextDouble();
        List<Double> pArray = transitionMatrix.get(state);
        double cP = 0;
        int i = 0;
        for (int pArraySize = pArray.size(); i < pArraySize; i++) {
            double d = pArray.get(i);
            cP += d;
            if (p < cP) {
                break;
            }
        }
        state = getState(i);
    }

    private String getState(int i) {
        switch (i) {
            case 0:
                return "Home";
            case 1:
                return "Visit";
            default:
                return "Post";
        }
    }

    public void homeAction() {
        logger.info("homeAction by [{}]", userName);
    }

    public void visitAction() {
        logger.info("visitAction by [{}]", userName);
    }

    public void postAction() {
        logger.info("postAction by [{}]", userName);
    }
}
