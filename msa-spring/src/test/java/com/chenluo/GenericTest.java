package com.chenluo;

import org.junit.jupiter.api.Test;

import software.amazon.ion.Decimal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class GenericTest {
    @Test
    public void testGeneric() {
        Class<?> clazz1 = LinkedList.class;
        Class clazz2 = ArrayList.class;
        clazz2 = clazz1;
    }

    @Test
    public void testGeneric2() {
        Class<?> clazz1 = ArrayList.class;
        Class clazz2 = ArrayList.class;
        assert List.class.isAssignableFrom(clazz1);
        assert List.class.isAssignableFrom(clazz2);

        clazz1 = clazz2;

        List<?> list = new ArrayList<>();
        //        list.add(list.get(0)); // compile error
        //        list.add(new Object()); // compile error
        List list2 = new ArrayList();
        list2.add(new Object());
        list2.add("");
    }

    @Test
    public void testStrFmt() {
        System.out.println("%s-%s".formatted(1, 2));
    }

    @Test
    void testBound() {
        testUpperBound(2);
    }

    public <T extends Number> void testUpperBound(T param) {
        System.out.println(param.intValue());
    }

    public void testSuperBound(Consumer<? super Decimal> param, List<? super Decimal> list) {
        BigDecimal bigDecimal = new BigDecimal(1);
        //        param.accept();
        param.accept(Decimal.valueOf(1));
        list.add(Decimal.valueOf(1));
        testSuperBound(null, new ArrayList<BigDecimal>()); // no compile error
        testSuperBound(null, new ArrayList<Decimal>()); // no compile error

        new ArrayList<Integer>()
                .sort(
                        Comparator.<Integer, Number>comparing(
                                (Number it) -> it.intValue(),
                                (Number c1, Number c2) -> c1.intValue() - c2.intValue()));
    }
}
