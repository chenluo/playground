/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.jvm.learn;

// ref: https://stackoverflow.com/questions/43668251/java-memory-model-volatile-and-x86#comment74385421_43669258
public class TestVolatile {

    private static int int1;
    private static int int2;
    private static volatile int int3_volatile;
    private static int int3_normal;
    private static int int4;
    private static int int5;

    private static int dummy;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++)
        {
            storeBarrierAfterStoreOfVolatileVariable();
            loadBarrierBeforeLoadOfVolatileVariable();
            storeBarrierAfterStoreOfVolatileVariable_compare();
            loadBarrierBeforeLoadOfVolatileVariable_compare();
            storeBarrierAfterInitOfFinalVariable(i);
        }
    }

    private static void storeBarrierAfterStoreOfVolatileVariable() {
        int1 = 1;
        int2 = 2;
        int3_volatile = 3;
        // lock addl here. lock is an instruction prefix. It means a memory-subsystem lock. Effectively a full memory barrier
        // aka mfence (composition of l(oad)fense and s(tore)fence)
        int4 = 4;
        int5 = 5;
    }

    private static void loadBarrierBeforeLoadOfVolatileVariable() {
        int int1Copy = int1;
        int int2Copy = int2;
        // on x86 seems nothing special to a non-volatile variable.
        // note: x86 is strong and has TSO. only storeLoad barrier is missing by TSO. So LoadLoad/LoadStore are not
        // necessary here. Thus no difference from the non-volatile case.
        // see: https://stackoverflow.com/questions/43668251/java-memory-model-volatile-and-x86#comment74385421_43669258
        int int3Copy = int3_volatile;
        int int4Copy = int4;
        int int5Copy = int5;
        dummy = int3Copy;
    }

    private static void storeBarrierAfterStoreOfVolatileVariable_compare() {
        int1 = 1;
        int2 = 2;
        int3_normal = 3;
        int4 = 4;
        int5 = 5;
    }

    private static void loadBarrierBeforeLoadOfVolatileVariable_compare() {
        int int1Copy = int1;
        int int2Copy = int2;
        int int3Copy = int3_normal;
        int int4Copy = int4;
        int int5Copy = int5;
        dummy = int3Copy;
    }

    private static void storeBarrierAfterInitOfFinalVariable(int i) {
        final int final_int = i+1;
        dummy = final_int>100?final_int:0;
    }
}