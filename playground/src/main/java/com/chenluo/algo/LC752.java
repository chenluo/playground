package com.chenluo.algo;

import java.util.ArrayDeque;
import java.util.Queue;

public class LC752 {
    public static void main(String[] args) {
        System.out.println(
                new Solution()
                        .openLock(new String[] {"0201", "0101", "0102", "1212", "2002"}, "0202"));
    }

    static class Solution {
        int[][] directions =
                new int[][] {
                    {-1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, -1, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, -1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, -1},
                    {0, 0, 0, 1}
                };
        int[] visited = new int[10000];
        Queue<Integer> queue = new ArrayDeque<>();

        public int openLock(String[] deadends, String target) {
            for (String s : deadends) {
                visited[toInt(s)] = 1;
            }
            if (visited[0] == 1) {
                return -1;
            }
            return bfs(toInt(target));
        }

        private int bfs(int target) {
            if (target == 0) {
                return 0;
            }

            queue.add(0);
            visited[0] = 1;
            int moves = 0;
            while (!queue.isEmpty()) {
                moves++;
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int cur = queue.poll();
                    for (int j = 0; j < directions.length; j++) {
                        int next = move(cur, directions[j]);
                        if (visited[next] == 0) {
                            if (next == target) {
                                return moves;
                            }
                            queue.add(next);
                            visited[next] = 1;
                        }
                    }
                }
            }

            return -1;
        }

        private int move(int current, int[] direction) {
            int divd = 1000;
            int result = 0;
            for (int i = 0; i < direction.length; i++) {
                result = result * 10;
                int cur = current / divd;
                cur += direction[i];
                if (cur > 9) {
                    cur = 0;
                }
                if (cur < 0) {
                    cur = 9;
                }
                result = result + cur;
                current %= divd;
                divd /= 10;
            }
            return result;
        }

        private int toInt(String target) {
            int result = 0;
            for (int i = 0; i < target.length(); i++) {
                result *= 10;
                int cur = target.charAt(i) - '0';
                result += cur;
            }
            return result;
        }
    }
}
