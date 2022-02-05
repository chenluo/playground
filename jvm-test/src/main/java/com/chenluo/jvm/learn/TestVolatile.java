/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.jvm.learn;

// ref: https://stackoverflow.com/questions/43668251/java-memory-model-volatile-and-x86#comment74385421_43669258
//      https://dzone.com/articles/memory-barriersfences
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
        // mfence prevent runtime/processor (effectively) *memory* reordering.
        // TODO: IMU, compiler reordering is more determine than processor reordering.
        // it actually change the order of some instructions.
        // processor reordering (at least for load/store) is introduced by store/load buffer between register and cpu cache
        // such buffers plays a role of async-fy the load and store instruction to cache.
        // So some memory instruction on 1 cpu won't visible for other cpus instantly.
        // after the buffered store instruction actually drained (cache receive the change), cache coherence protocol then
        // ensure the change will visible to other cpus.
        // TODO: is buffer level the root cause of the effective order of instruction?
        // 3 kinds of reordering:
        // 1. compiler
        // 2. cpu execution optimization -> instruction reordering
        // 3. store buffer caused "reordering" -> memory reordering
        // So, no for this question because of the existence of 2nd reordering.
        // TODO: does cpu memory barrier exist before buffer level introduced?
        // as 3 kinds of reordering, I guess before store buffer, mfence simply means no instruction reordering.
        // After introducing store buffer, drain buffer effectively means no instruction reordering
        // and instruction need visible to other cpus.
        //
        // mfence: full barrier, wait for drain all load and store buffer when meet it.
        // Seems, the load and store instruction are executed orderly as it presents in the assembly code.
        // But the instruction in the buffer is not visible to cache and may disorder.
        // mfence requires the load/store before it visible to cache which means the the instruction before it must happen
        // before the instructions after it. Thus prevent "reordering" or random visible order of the instruction on the 2 sides.
        // the instructions after mfence always become visible to other cpus/cache later than the ones before the mfence.
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
        // TSO: total store order
        // x86 load has acquire semantics: loadStore, loadLoad
        // x86 store has release semantics: loadStore, storeStore
        //     ===> x86 only need storeLoad barrier.
        // see: https://preshing.com/20120612/an-introduction-to-lock-free-programming/
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