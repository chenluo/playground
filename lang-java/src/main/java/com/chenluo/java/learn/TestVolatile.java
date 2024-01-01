/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0 and the Server Side Public License, v 1; you may not use this file except
 * in compliance with, at your election, the Elastic License 2.0 or the Server
 * Side Public License, v 1.
 */

package com.chenluo.java.learn;

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
        for (int i = 0; i < 10000; i++) {
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
        final int final_int = i + 1;
        dummy = final_int > 100 ? final_int : 0;
    }
}

//
// ./jdk/hotspot/src/share/vm/c1/c1_LIRGenerator.cpp -> generate LIR_Op0
// ./jdk/hotspot/src/share/vm/c1/c1_LIRAssembler.cpp -> emit LIR_OP to assemble
//                                                   -> void LIR_Assembler::emit_op0(LIR_Op0* op)
// ./jdk/hotspot/src/cpu/x86/vm/c1_LIRAssembler_x86.cpp -> actual emission behavior from LIR_OP to assemble in x86 arch
//                                                      -> void LIR_Assembler::membar() {
//                                                      ->     __ membar( Assembler::Membar_mask_bits(Assembler::StoreLoad));
//                                                      -> }
// ./jdk/hotspot/src/cpu/x86/vm/assembler_x86.hpp -> membar(...) defined here
//                                                -> x86 assemble code is write here.
/*
//------------------------field access--------------------------------------

// Comment copied form templateTable_i486.cpp
// ----------------------------------------------------------------------------
// Volatile variables demand their effects be made known to all CPU's in
// order.  Store buffers on most chips allow reads & writes to reorder; the
// JMM's ReadAfterWrite.java test fails in -Xint mode without some kind of
// memory barrier (i.e., it's not sufficient that the interpreter does not
// reorder volatile references, the hardware also must not reorder them).
//
// According to the new Java Memory Model (JMM):
// (1) All volatiles are serialized wrt to each other.
// ALSO reads & writes act as aquire & release, so:
// (2) A read cannot let unrelated NON-volatile memory refs that happen after
// the read float up to before the read.  It's OK for non-volatile memory refs
// that happen before the volatile read to float down below it.
// (3) Similar a volatile write cannot let unrelated NON-volatile memory refs
// that happen BEFORE the write float down to after the write.  It's OK for
// non-volatile memory refs that happen after the volatile write to float up
// before it.
//
// We only put in barriers around volatile refs (they are expensive), not
// _between_ memory refs (that would require us to track the flavor of the
// previous memory refs).  Requirements (2) and (3) require some barriers
// before volatile stores and after volatile loads.  These nearly cover
// requirement (1) but miss the volatile-store-volatile-load case.  This final
// case is placed after volatile-stores although it could just as well go
// before volatile-loads.


    void LIRGenerator::do_StoreField(StoreField* x) {
        bool is_volatile = x->field()->is_volatile();

        ......

        if (is_volatile || needs_patching) {
            // load item if field is volatile (fewer special cases for volatiles)
            // load item if field not initialized
            // load item if field not constant
            // because of code patching we cannot inline constants
            if (field_type == T_BYTE || field_type == T_BOOLEAN) {
                value.load_byte_item();
            } else  {
                value.load_item();
            }
        } else {
            value.load_for_store(field_type);
        }

        ......

        if (is_volatile && os::is_MP()) {
            __ membar_release();  --> release semantics: prepare for subsequent store, the memory access before this barrier must
                                      visible to cache
                                  --> storeStore & loadStore barrier here.
                                  --> in x86 both are nop because x86 doesn't reorder loadStore & storeStore memory order.
        }

        ......

        if (is_volatile && !needs_patching) {
            volatile_field_store(value.result(), address, info); --> seems actual store op, execute mov inst
                                                                 --> as store buffer, the store op may not invalidate
                                                                 --> cached lines in other cpus thus a delay/async
                                                                 --> of visibility.
        } else {
            LIR_PatchCode patch_code = needs_patching ? lir_patch_normal : lir_patch_none;
            __ store(value.result(), address, info, patch_code);
        }


        if (is_volatile && os::is_MP()) {
            __ membar(); --> storeLoad barrier enough but full memory barrier actually.
                         --> means the store op must be visible to other cpus before this barrier
                         --> what does it actually do: wait for store buffer drained. That means all other cpus
                         --> invalidate any cached line includes the store address.
                         --> in x86 it should be a mfence (only supported in sse2)
                         --> actually a lock-prefixed nop (addl 0 to register rsp) instead
                         --> lock prefix means atomic instruction which has semantics of full barrier
        }
    }


    void LIRGenerator::do_LoadField(LoadField* x) {

        bool is_volatile = x->field()->is_volatile();

        ......

        if (is_volatile && !needs_patching) {
            volatile_field_load(address, reg, info);
        } else {
            LIR_PatchCode patch_code = needs_patching ? lir_patch_normal : lir_patch_none;
            __ load(address, reg, info, patch_code);
        }

        if (is_volatile && os::is_MP()) {
            __ membar_acquire(); --> acquire semantics, the subsequent memory access should not reorder to before the target
                                 --> load memory access.
                                 --> LoadStore & loadLoad barrier
        }
    }
*/
