package com.chenluo.test;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Playground {
    public static void main(String[] args) {
        List<Object> objectList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        stringList.add("1");
        // objectList = stringList; // illegal, List<String> is not extending List<Object>.
        // up cast failed.
        // addAll like read the elements in the parameter and write it
        // read a subtype as supertype is safe. Object a = "string"
        // so the parameter must be a supertype of List<T> --> List<? extends T>
        objectList.addAll(stringList); // List<String> extends List<? extends Object>
        objectList.addAll(integerList); // List<Integer> extends List< ? extends Object>
        // what ever type of element put in objectList is safe to read as Object later.
        // but we lose the actual type of string/integer.
        for (Object o : objectList) { // read element as object is safe.
            System.out.println((String) o);
            System.out.println((Integer) o);
        }

        List<? super String> superStringList = new ArrayList<>();
        superStringList.add("a");
        CharSequence s = "a";
        // superStringList.add(s);
        Object s1 = superStringList.get(0);
        for (Object o : superStringList) {
            System.out.println((String) o);
        }

        List<CharSequence> charSequenceList = new ArrayList<>();
        charSequenceList.add(s);
        // super means safe to write the subtype to supertype --> kotlin's in --> consumer --> write
        // extends means safe to read the subtype as supertype --> kotlin's out --> producer -->
        // read
        superStringList = objectList; // List<Object> extends List<? super String>
        superStringList = stringList; // List<String> extends List<? super String>
        superStringList =
                charSequenceList; // String extends CharSequence --> List<CharSequence> extends
        // List<? super String>
        //        superStringList.addAll(charSequenceList);
        //        charSequenceList.addAll(superStringList);
        //        superStringList = integerList;
        //        objectList = superStringList;
        //        stringList = superStringList;
        //        integerList = superStringList;

        List<? extends Object> extendObjectList = new ArrayList<>();
        extendObjectList = objectList;
        //        extendObjectList.addAll(stringList);
        //        extendObjectList.add("string");
        extendObjectList = stringList;
        //        stringList = extendObjectList;

        List<? extends String> extendCharSeqList = new ArrayList<>();

        //        extendCharSeqList = superStringList;
        List<String> a;
        StringList b = null;
        a = b;
    }

    class StringList implements List<String> {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NotNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NotNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NotNull
        @Override
        public <T> T[] toArray(@NotNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NotNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NotNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NotNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NotNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NotNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {}

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {}

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @NotNull
        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NotNull
        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @NotNull
        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    }
}
