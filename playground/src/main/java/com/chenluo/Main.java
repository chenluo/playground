package com.chenluo;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    private final String lock = "Thisisalock";

    private volatile int volatileInt = 0;

    private int nonVolatileInt = 0;

    public static void main(String[] args) {
        String s = new Solution().minWindow(
                new StringBuilder("ADOBECODEBANC").reverse().toString(), "ABC");
        System.out.println(s);
    }

    private void testMarkWord() {
        volatileInt = 1;
        nonVolatileInt = 2;
        System.out.println("init");
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        System.out.println("single thread enter the critical area");
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        ExecutorService executor = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), new ThreadPoolExecutor.AbortPolicy());
        Runnable t1 = () -> {
            int count = 1;
            while (count-- != 0) {
                synchronized (lock) {
                    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }
        };

        Callable<Boolean> callable = () -> {
            try {
                t1.run();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        };

        Runnable t2 = () -> {
            int count = 1;
            while (count-- != 0) {
                synchronized (lock) {
                    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }
            }

        };

        executor.execute(t1);
        executor.execute(t2);

        executor.shutdown();

    }

    private void testJol() {
        List<List<String>> stringList = new ArrayList<>();
        for (int i = 0; i < 1e5; i++) {
            List<String> subList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                subList.add(i + String.valueOf(j));
            }
            stringList.add(subList);
        }
        GraphLayout graphLayout = GraphLayout.parseInstance(stringList);
        System.out.println(graphLayout.totalSize() / 1000 / 1000);
    }

    static class Solution {
        public String minWindow(String s, String t) {
            List<Character> charSet = findChar(t);
            Map<Character, Integer> idxMap = new HashMap<>();
            int i = 0;
            for (char c : charSet) {
                idxMap.put(c, i);
                i++;
            }
            int[] targetCount = new int[charSet.size()];
            for (i = 0; i < t.length(); i++) {
                targetCount[idxMap.get(t.charAt(i))] = targetCount[idxMap.get(t.charAt(i))] + 1;
            }

            int[][] countL2R = new int[s.length() + 1][charSet.size()];
            for (i = 0; i < s.length(); i++) {
                System.arraycopy(countL2R[i], 0, countL2R[i + 1], 0, charSet.size());
                char c = s.charAt(i);
                if (idxMap.containsKey(c)) {
                    countL2R[i + 1][idxMap.get(c)] = countL2R[i][idxMap.get(c)] + 1;
                }
            }

            int[][] countR2L = new int[s.length() + 1][charSet.size()];
            for (i = 0; i < s.length(); i++) {
                System.arraycopy(countR2L[i], 0, countR2L[i + 1], 0, charSet.size());
                char c = s.charAt(s.length() - 1 - i);
                if (idxMap.containsKey(c)) {
                    countR2L[i + 1][idxMap.get(c)] = countR2L[i][idxMap.get(c)] + 1;
                }
            }

            int minLength = s.length() + 1;
            int startIdx = -1;
            int endIdx = -1;

            for (i = 0; i < s.length(); i++) {
                if (valid(countL2R, i, s.length(), targetCount)) {
                    startIdx = i;
                } else {
                    break;
                }
            }
            if (startIdx == -1) {
                return "";
            }

            for (i = s.length() - 1; i >= 0; i--) {
                if (valid(countL2R, startIdx, i + 1, targetCount)) {
                    endIdx = i + 1;
                } else {
                    break;
                }
            }
            String ans1 = null;

            if (endIdx != -1 && startIdx < endIdx) {
                ans1 = s.substring(startIdx, endIdx);
            }

            for (i = s.length() - 1; i >= 0; i--) {
                if (valid(countL2R, 0, i + 1, targetCount)) {
                    endIdx = i + 1;
                } else {
                    break;
                }
            }
            if (endIdx == -1) {
                return "";
            }

            for (i = 0; i < s.length(); i++) {
                if (valid(countL2R, i, endIdx, targetCount)) {
                    startIdx = i;
                } else {
                    break;
                }
            }

            String ans2 = null;

            if (startIdx != -1 && startIdx < endIdx) {
                ans2 = s.substring(startIdx, endIdx);
            }
            if (ans2 != null && ans1 != null) {
                return ans1.length() > ans2.length() ? ans2 : ans1;
            }
            if (ans1 != null) {
                return ans1;
            }
            return ans2;

        }

        private List<Character> findChar(String t) {
            Set<Character> charSet = new HashSet<>();
            for (int i = 0; i < t.length(); i++) {
                charSet.add(t.charAt(i));
            }
            return new ArrayList<>(charSet);
        }

        private boolean valid(int[][] count, int i, int j, int[] targetCount) {

            int[] tempCount = new int[targetCount.length];
            for (int k = 0; k < targetCount.length; k++) {
                tempCount[k] = count[j][k] - count[i][k];
            }
            for (int k = 0; k < targetCount.length; k++) {
                if (targetCount[k] > tempCount[k]) {
                    return false;
                }
            }
            return true;
        }
    }

}
