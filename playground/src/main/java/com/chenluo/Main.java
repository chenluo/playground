package com.chenluo;

import io.netty.example.http.helloworld.HttpHelloWorldServer;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    private final String lock = "Thisisalock";

    private volatile int volatileInt = 0;

    private int nonVolatileInt = 0;

    public static void main(String[] args) throws Exception {
//        HttpHelloWorldServer.main(new String[1]);
        for (int i = 0; i < 120; i++)
            System.out.println(new Main().perm(5, i));
    }

    /**
     * 1,2,3,4,5,6
     * 0
     * < 5*4*3*2*1 -> 1
     * < 4*3*2*1 ->2
     * < 3*2*1 -> 3
     * < 2*1 -> 4
     * < 1 -> 5
     * only 1 left -> 6
     *
     * 1
     * < 5*4*3*2*1 -> 1
     * < 4*3*2*1 ->2
     * < 3*2*1 -> 3
     * < 2*1 -> 4
     * >= 1 -> 6
     * only 1 left -> 5
     * @return
     */

    List<Integer> perm(int n, int idx) {
        List<Integer> result = new ArrayList<>();

        int remained = n;
        while (remained > 0) {
            int base = 1;
            for (int i = 1; i <= remained-1; i++) {
                base*=i;
            }
            int curIdx = idx/base;
            int tempIdx = 0;
            for (int i = 0; i < n; i++) {
                if (result.contains(i)) {
                    continue;
                }
                if (tempIdx == curIdx) {
                    result.add(i);
                    remained--;
                    break;
                }
                tempIdx++;
            }
            idx%=base;
        }
        return result;
    }



    public char intToChar() {
        int a = 1;
        System.out.println((char) ('a'+1));
        return (char) ('a'+1);
    }

    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i < word1.length()+1; i++) {
            for (int j = 1; j < word2.length()+1; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j]+1, dp[i][j-1]+1));
                } else {
                    dp[i][j] = Math.min(dp[i-1][j-1]+2, Math.min(dp[i-1][j]+1, dp[i][j-1]+1));
                }
            }
        }
        return dp[word1.length()][word2.length()];
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
        while (true) {
            try {
                if (!!executor.awaitTermination(100, TimeUnit.SECONDS)) break;
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

        }

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
