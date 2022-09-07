package com.chenluo.test;

import java.util.ArrayList;
import java.util.List;

public class Playground {
    public static void main(String[] args) {
        List<Object> objectList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        stringList.add("1");
//        objectList = stringList; // illegal, List<String> is not extending List<Object>. up cast failed.
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
//        superStringList.add(s);
        Object s1 = superStringList.get(0);
        for (Object o : superStringList) {
            System.out.println((String) o);
        }

        List<CharSequence> charSequenceList = new ArrayList<>();
        charSequenceList.add(s);
        // super means safe to write the subtype to supertype --> kotlin's in --> consumer --> write
        // extends means safe to read the subtype as supertype --> kotlin's out --> producer --> read
        superStringList = objectList; // List<Object> extends List<? super String>
        superStringList = stringList; // List<String> extends List<? super String>
        superStringList = charSequenceList; // String extends CharSequence --> List<CharSequence> extends List<? super String>
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
    }
}
