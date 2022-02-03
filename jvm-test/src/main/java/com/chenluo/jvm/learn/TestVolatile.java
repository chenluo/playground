/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.jvm.learn;

public class TestVolatile {

    private static int int1;
    private static int int2;
    private static volatile int int3;
    private static int int4;
    private static int int5;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++)
        {
            increase(i);
        }
    }

    private static void increase(int i) {
        int1 = i + 1;
        int2 = i + 2;
        int3 = i + 3;
        int4 = i + 4;
        int5 = i + 5;
    }
}