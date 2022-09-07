package com.chenluo.algo;

public class LC79 {
    public static void main(String[] args) {
        char[][] chars = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        System.out.println(new Solution().exist(chars, "EEDASABCESCF"));
    }
    static class Solution {
        public boolean exist(char[][] board, String word) {
            boolean[][] visited = new boolean[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    visited[i][j] = true;
                    boolean found = find(board,i,j, word, 0, visited);
                    visited[i][j] = false;
                    if (found) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean find(char[][] board, int i, int j,
                             String word, int cur, boolean[][] visited) {
            if (cur >= word.length()) {
                return true;
            }
            if (board[i][j] != word.charAt(cur)) {
                return false;
            } else if (cur == word.length()-1) {
                return true;
            }
            int[][] direction = new int[][]{{-1,0}, {1,0}, {0,-1}, {0, 1}};
            for (int ii = 0; ii < direction.length; ii++) {
                int newI = i+direction[ii][0];
                int newJ = j+direction[ii][1];
                if (newI >=0 && newI < board.length && newJ >= 0 && newJ < board[0].length && visited[newI][newJ] == false) {
                    visited[newI][newJ] = true;
                    boolean find = find(board, newI, newJ, word, cur+1, visited);
                    visited[newI][newJ] = false;
                    if (find) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
