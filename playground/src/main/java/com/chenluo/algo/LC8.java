package com.chenluo.algo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LC8 {
    public static void main(String[] args) {
        //        System.out.println(new Solution().myAtoi("2147483648"));
        //        ArrayDeque<Integer> a = new ArrayDeque<>();
        //        a.addLast(1);
        //        a.removeLast();
        //        a.peekLast();
        //        a.pop();
        //        a.removeFirst();
        //        Map<String, String> map = new HashMap<>();
        BigDecimal bigDecimal = new BigDecimal("10.21");
        NumberFormat numberFormat = new DecimalFormat("0.#");
        numberFormat.setRoundingMode(RoundingMode.CEILING);
        System.out.println(numberFormat.format(bigDecimal));
    }

    static class Solution {
        public int myAtoi(String s) {
            int phase = 0; // 0 init, 1 after read sign, 2 after apply the sign
            int curNumber = 0;
            int flag = 1;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (phase == 1 || phase == 2) {
                    if (ch <= '9' && ch >= '0') {
                        if (phase == 1 && ch != '0') {
                            curNumber *= 10;
                            curNumber += ch - '0';
                            curNumber *= flag;
                            phase = 2;
                            continue;
                        }
                        int tmp = curNumber;
                        curNumber *= 10;
                        if (curNumber / 10 != tmp) {
                            return flag == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                        }
                        if (flag == 1) {
                            curNumber += ch - '0';
                        } else {
                            curNumber -= ch - '0';
                        }
                        if (flag == 1 && ((curNumber - ch + '0') / 10 != tmp || curNumber < 0)) {
                            return Integer.MAX_VALUE;
                        }
                        if (flag == -1 && (curNumber + ch - '0') / 10 != tmp || curNumber < 0) {
                            return Integer.MIN_VALUE;
                        }
                    } else {
                        return curNumber;
                    }
                } else {
                    if (ch == '-') {
                        flag = -1;
                        phase = 1;
                    } else if (ch == '+') {
                        phase = 1;
                    } else if (ch <= '9' && ch > '0') {
                        // first number
                        phase = 2;
                        curNumber = flag * (ch - '0');
                    } else if (ch == ' ') {

                    } else if (ch == '0') {
                        phase = 2;
                    } else {
                        return 0;
                    }
                }
            }
            return curNumber;
        }
    }
}
