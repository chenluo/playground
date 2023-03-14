/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo;

import com.chenluo.client.MyNettyClient;
import com.chenluo.server.MyNettyServer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.net.URISyntaxException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class NettyMainTest {

    private Logger logger = LoggerFactory.getLogger(NettyMainTest.class);

    @Test
    public void testNettyMain() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(500), new ThreadFactory() {
            private AtomicInteger threadNo = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "thread-" + threadNo.incrementAndGet());
            }
        },
                new ThreadPoolExecutor.CallerRunsPolicy());

        executor.submit(() -> {
            new MyNettyServer().main(new String[0]);
        });

        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 100000; i++) {
            try {
                executor.submit(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            new MyNettyClient().fastTest();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (SSLException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (RejectedExecutionException e) {
                logger.error("task rejected", e);
                longAdder.increment();
            }
        }

        executor.shutdown();
        while (true) {
            try {
                if (executor.awaitTermination(10, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
