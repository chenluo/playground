package com.chenluo;

import com.google.common.base.Supplier;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

public class Main {

    private final String lock = "Thisisalock";

    private volatile int volatileInt = 0;

    private int nonVolatileInt = 0;

    public static void main(String[] args) throws Exception {
//        HttpHelloWorldServer.main(new String[1]);
        for (int i = 0; i < 120; i++)
            System.out.println(new Main().perm(5, i));
        String s = "s";
        Comparator<String> stringStringBiConsumer = (a, b) -> {
            System.out.println(args[0]);
            return 1;
        };
        new Main().testLambda(s);
        s = "ss";
        System.out.println(s);
    }

    public void testLambda(String s) {
        Supplier<Integer> integerSupplier = () -> {
            System.out.println(s);
            return 1;
        };
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
     * <p>
     * 1
     * < 5*4*3*2*1 -> 1
     * < 4*3*2*1 ->2
     * < 3*2*1 -> 3
     * < 2*1 -> 4
     * >= 1 -> 6
     * only 1 left -> 5
     *
     * @return
     */

    List<Integer> perm(int n, int idx) {
        List<Integer> result = new ArrayList<>();

        int remained = n;
        while (remained > 0) {
            int base = 1;
            for (int i = 1; i <= remained - 1; i++) {
                base *= i;
            }
            int curIdx = idx / base;
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
            idx %= base;
        }
        return result;
    }


    public char intToChar() {
        int a = 1;
        System.out.println((char) ('a' + 1));
        return (char) ('a' + 1);
    }

    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i < word1.length() + 1; i++) {
            for (int j = 1; j < word2.length() + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + 2, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
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

    class BestBuyAndSellStock3 {
        // dp[i][j]: the max profit if we do i-th *sell* at j-th day (including buy and sell in the same day)
        // some unhelpful notes:
        // for case i > j, it's possible for this problem, because allow buy and sell in the same day;
        // for case i < j, for i-th sell, we must have i-1 sell before and current problem only depends on the result of i-1-th sell, dp[i][...] -> dp[i-1][...]
        // dp[i][j] = max{
        //               dp[i-1][0] -> always 0.
        //.              dp[i-1][1] + prices[j]-prices[1] -> because we can buy and sell in the same day, we doesn't care such cases like at day 1 we buy and sell mutiple times.
        //.              dp[i-1][1] -> not buy and sell
        //.              ......
        //               dp[i-1][j-1] + prices[j]-prices[j-1]
        //               dp[i-1][j-1] -> not buy and sell,
        //               dp[i][j-1] -> >= dp[i-1][j-k]. we may remove above `not buy and sell` case
        //               }
        // we can find the max profit by go through dp[i][j].
        public int maxProfit(int[] prices) {
            // greedy 
            // lowest and highest way
            int buyPrice = -1;
            int sellPrice = -1;
            int result = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                if (prices[i] < prices[i + 1]) {
                    if (buyPrice == -1) {
                        buyPrice = prices[i];
                    }
                } else {
                    if (buyPrice != -1) {
                        sellPrice = prices[i];
                        result += sellPrice - buyPrice;
                        buyPrice = -1;
                        sellPrice = -1;
                    }
                }
            }
            if (buyPrice != -1) {
                result += prices[prices.length - 1] - buyPrice;
            }

            return result;
        }

        // n^2 time
        // n space
        public int maxProfitN2ReducedSpace(int[] prices) {
            // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
            int[] dp = new int[prices.length];
            int result = 0;
            for (int i = 1; i < prices.length; i++) { // iter i sell ops
                int max = dp[0] - prices[0];
                for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                    int temp = dp[j];
                    dp[j] = Math.max(max, max + prices[j]);
                    if (j > 0) dp[j] = Math.max(dp[j], dp[j - 1]);
                    max = Math.max(max, dp[j] - prices[j]);

                    if (dp[j] > result) {
                        result = dp[j];
                    }
                }
            }
            return result;
        }

        // n^2 time
        // n^2 space
        public int maxProfitN2Version(int[] prices) {
            // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
            int[][] dp = new int[prices.length + 1][prices.length];
            int result = 0;
            for (int i = 1; i < prices.length; i++) { // iter i sell ops
                int max = dp[i - 1][0] - prices[0];
                for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                    // for (int k = 0; k < j; k++) {
                    //     dp[i][j] = Math.max(dp[i-1][k], Math.max(dp[i-1][k] + prices[j]-prices[k], dp[i][j]));
                    // }
                    // max(dp[i-1][j])
                    // global case:  dp[i][j-1]
                    // example, for j = 1,
                    // 1st: {dp[i-1][0]+p_1-p_0} -> only 1 compare
                    // for j = 2
                    // 1st: dp[i-1][0]+p_2-p_0
                    // 2nd: dp[i-1][1]+p_2-p_1 -> if chosen
                    // for j = 3
                    // 1st: dp[i-1][0]+p_3-p_0
                    // 2nd: dp[i-1][1]+p_3-p_1
                    // 3rd: dp[i-1][2]+p_3-p_2
                    // there some how repeat compare... the key is found the smallest prices before j and then just compare it with not sell case.
                    // exchange dp[i-1][k]+price[i]-price[k]
                    // -> price[i]+dp[i-1][k]-price[k]
                    // -> price[i]   +    dp[i-1][k]-price[k]
                    //                    (this item only changes with k)

                    dp[i][j] = Math.max(max, max + prices[j]);
                    if (j > 0) dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                    max = Math.max(max, dp[i - 1][j] - prices[j]);

                    if (dp[i][j] > result) {
                        result = dp[i][j];
                    }
                }
            }
            return result;
        }

        // n^3 time
        // n^2 space
        public int maxProfitMostVanlliaVersion(int[] prices) {
            // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
            int[][] dp = new int[prices.length + 1][prices.length];
            int result = 0;
            for (int i = 1; i < prices.length; i++) { // iter i sell ops
                for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                    for (int k = 0; k < j; k++) {
                        dp[i][j] = Math.max(dp[i - 1][k], Math.max(dp[i - 1][k] + prices[j] - prices[k], dp[i][j]));
                    }

                    if (dp[i][j] > result) {
                        result = dp[i][j];
                    }
                }
            }
            return result;
        }
    }

}

class Solution {
    // dp[i][j]: the max profit if we do i-th *sell* at j-th day (including buy and sell in the same day)
    // some unhelpful notes:
    // for case i > j, it's possible for this problem, because allow buy and sell in the same day;
    // for case i < j, for i-th sell, we must have i-1 sell before and current problem only depends on the result of i-1-th sell, dp[i][...] -> dp[i-1][...]
    // dp[i][j] = max{
    //               dp[i-1][0] -> always 0.
    //.              dp[i-1][1] + prices[j]-prices[1] -> because we can buy and sell in the same day, we doesn't care such cases like at day 1 we buy and sell mutiple times.
    //.              dp[i-1][1] -> not buy and sell
    //.              ......
    //               dp[i-1][j-1] + prices[j]-prices[j-1]
    //               dp[i-1][j-1] -> not buy and sell,
    //               dp[i][j-1] -> >= dp[i-1][j-k]. we may remove above `not buy and sell` case
    //               }
    // we can find the max profit by go through dp[i][j].
    public int maxProfit(int[] prices) {
        // greedy 
        // lowest and highest way
        int buyPrice = -1;
        int sellPrice = -1;
        int result = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i + 1]) {
                if (buyPrice == -1) {
                    buyPrice = prices[i];
                }
            } else {
                if (buyPrice != -1) {
                    sellPrice = prices[i];
                    result += sellPrice - buyPrice;
                    buyPrice = -1;
                    sellPrice = -1;
                }
            }
        }
        if (buyPrice != -1) {
            result += prices[prices.length - 1] - buyPrice;
        }

        return result;
    }

    // n^2 time
    // n space
    public int maxProfitN2ReducedSpace(int[] prices) {
        // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
        int[] dp = new int[prices.length];
        int result = 0;
        for (int i = 1; i < prices.length; i++) { // iter i sell ops
            int max = dp[0] - prices[0];
            for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                int temp = dp[j];
                dp[j] = Math.max(max, max + prices[j]);
                if (j > 0) dp[j] = Math.max(dp[j], dp[j - 1]);
                max = Math.max(max, dp[j] - prices[j]);

                if (dp[j] > result) {
                    result = dp[j];
                }
            }
        }
        return result;
    }

    // n^2 time
    // n^2 space
    public int maxProfitN2Version(int[] prices) {
        // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
        int[][] dp = new int[prices.length + 1][prices.length];
        int result = 0;
        for (int i = 1; i < prices.length; i++) { // iter i sell ops
            int max = dp[i - 1][0] - prices[0];
            for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                // for (int k = 0; k < j; k++) {
                //     dp[i][j] = Math.max(dp[i-1][k], Math.max(dp[i-1][k] + prices[j]-prices[k], dp[i][j]));
                // }
                // max(dp[i-1][j])
                // global case:  dp[i][j-1]
                // example, for j = 1,
                // 1st: {dp[i-1][0]+p_1-p_0} -> only 1 compare
                // for j = 2
                // 1st: dp[i-1][0]+p_2-p_0
                // 2nd: dp[i-1][1]+p_2-p_1 -> if chosen
                // for j = 3
                // 1st: dp[i-1][0]+p_3-p_0
                // 2nd: dp[i-1][1]+p_3-p_1
                // 3rd: dp[i-1][2]+p_3-p_2
                // there some how repeat compare... the key is found the smallest prices before j and then just compare it with not sell case.
                // exchange dp[i-1][k]+price[i]-price[k]
                // -> price[i]+dp[i-1][k]-price[k]
                // -> price[i]   +    dp[i-1][k]-price[k]
                //                    (this item only changes with k)

                dp[i][j] = Math.max(max, max + prices[j]);
                if (j > 0) dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                max = Math.max(max, dp[i - 1][j] - prices[j]);

                if (dp[i][j] > result) {
                    result = dp[i][j];
                }
            }
        }
        return result;
    }

    // n^3 time
    // n^2 space
    public int maxProfitMostVanlliaVersion(int[] prices) {
        // length+1 * length, an additional "day" for the initial state. just zero profit before the first sell actually occurrs.
        int[][] dp = new int[prices.length + 1][prices.length];
        int result = 0;
        for (int i = 1; i < prices.length; i++) { // iter i sell ops
            for (int j = 0; j < prices.length; j++) { // iter j sell points/days
                for (int k = 0; k < j; k++) {
                    dp[i][j] = Math.max(dp[i - 1][k], Math.max(dp[i - 1][k] + prices[j] - prices[k], dp[i][j]));
                }

                if (dp[i][j] > result) {
                    result = dp[i][j];
                }
            }
        }
        return result;
    }
}
