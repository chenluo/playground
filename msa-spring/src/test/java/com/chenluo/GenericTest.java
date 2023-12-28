package com.chenluo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
}
