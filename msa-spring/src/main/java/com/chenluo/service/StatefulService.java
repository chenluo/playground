package com.chenluo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StatefulService {
    private final AtomicInteger counter = new AtomicInteger();

    public void increase() {
        counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }
}
