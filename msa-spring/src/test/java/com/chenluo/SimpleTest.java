package com.chenluo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SimpleTest {
    @Test
    public void bit() {
        System.out.println(-0b1111 & 0b1111);
        System.out.println(-0b1111 | 0b1111);
        System.out.println(0b1111 & 0b1111);
        System.out.println(-0b1111);
        int a = 0b10000000000000000000000000000001;
        System.out.println(a);
    }

    @Test
    public void Q148() {
        List<Integer> l = List.of(4, 19, 14, 5, -3, 1, 8, 5, 11, 15);
        ListNode dummy = new ListNode(0);
        ListNode tmp = dummy;
        for (int i = 0; i < l.size(); i++) {
            tmp.next = new ListNode(l.get(i));
            tmp = tmp.next;
        }
        new Solution148().sortList(dummy.next);
        System.out.println(dummy.next);
    }

    @Test
    public void Q76() {
        System.out.println(new Solution76().minWindow("bdab", "ab"));
    }


    @Test
    public void Q227() {
        //        int calculate = new Solution().calculate("1+2/1");
        //        System.out.println(calculate);
        //        calculate = new Solution().calculate("1+2*1");
        //        System.out.println(calculate);
        //        System.out.println(new Solution().calculate("3+2*2+3"));
        //        System.out.println(new Solution().calculate("31"));
        //        System.out.println(new Solution().calculate("0-2147483647"));
        System.out.println(new Solution().calculate("1-1+1"));

    }

    @Test
    public void Q33() {
        System.out.println(new Solution33().search(new int[]{2, 3, 4, 1}, 4));
        //        System.out.println(new Solution33().search(new int[]{1,2, 3, 4}, 1));
    }

    @Test
    public void Q84() {
        Assertions.assertEquals(12,
                new Solution84().largestRectangleArea(new int[]{2, 1, 5, 3, 5, 3}));
    }

    @Test
    public void Q567() {
        Assertions.assertTrue(new Solution567().checkInclusion("adc", "dcda"));
    }

    @Test
    public void Q139() {
        Assertions.assertTrue(new Solution139().wordBreak("leetcode", List.of("leet", "code")));
    }

    @Test
    public void Q207() {
        Assertions.assertTrue(new Solution207().canFinish(3, new int[][]{{1, 0}}));
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    class Solution148 {
        public ListNode sortList(ListNode head) {
            int len = 0;
            ListNode tmp = head;
            while (tmp != null) {
                len++;
                tmp = tmp.next;
            }
            if (len <= 1) {
                return head;
            }

            qs(head, len);
            return head;
        }

        private void qs(ListNode start, int len) {
            if (start == null || len == 0) {
                return;
            }
            int pivot = partition(start, len);
            // left
            qs(start, pivot);

            // right
            int rightCount = len - pivot - 1;
            ListNode rightAfterPivot = start;
            while (pivot >= 0) {
                rightAfterPivot = rightAfterPivot.next;
                pivot--;
            }
            qs(rightAfterPivot, rightCount);
        }

        private int partition(ListNode start, int len) {

            int pivotVal = start.val;
            ListNode tmp = start;
            ListNode pivotNode = start;
            ListNode midNode = start;
            for (int i = 0; i < len; i++) {
                if (i == len / 2) {
                    pivotVal = tmp.val;
                    midNode = tmp;
                }
                if (i == len - 1) {
                    pivotNode = tmp;
                    midNode.val = pivotNode.val;
                    pivotNode.val = pivotVal;
                }
                tmp = tmp.next;
            }
            int pivotIdx = 0;
            ListNode ptr = start;
            tmp = start;
            for (int i = 0; i < len; i++) {
                if (ptr.val < pivotVal) {
                    int tmpVal = tmp.val;
                    tmp.val = ptr.val;
                    tmp = tmp.next;
                    ptr.val = tmpVal;
                    pivotIdx++;
                }
                ptr = ptr.next;
            }
            // swap tmp and pivot
            int tmpVal = tmp.val;
            tmp.val = pivotVal;
            pivotNode.val = tmpVal;


            return pivotIdx;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    class Solution {
        Stack<String> stack = new Stack<>();

        public int calculate(String s) {
            for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);
                String num = "";
                if (isNum(cur)) {
                    while (i < s.length()) {
                        if (isNum(s.charAt(i))) {
                            num += s.charAt(i);
                            i++;
                        } else {
                            break;
                        }
                    }
                    stack.push(num);
                    i--;
                }
                if (isOperator(cur)) {
                    if (isMulDiv(cur)) {
                        i++;
                        num = "";
                        while (i < s.length()) {
                            if (isNum(s.charAt(i))) {
                                num += s.charAt(i);
                                i++;
                            } else {
                                break;
                            }
                        }
                        i--;
                        if (cur == '*') {
                            stack.push(String.valueOf(
                                    Integer.parseInt(stack.pop()) * Integer.parseInt(num)));
                        } else {
                            stack.push(String.valueOf(
                                    Integer.parseInt(stack.pop()) / Integer.parseInt(num)));
                        }
                    } else {
                        stack.push("" + cur);
                    }
                }
            }
            while (!(stack.size() == 1)) {
                int n1 = Integer.valueOf(stack.pop());
                String op = stack.pop();
                int n2 = Integer.valueOf(stack.pop());
                if (op.equals("+")) {
                    stack.push(String.valueOf(n1 + n2));
                } else {
                    stack.push(String.valueOf(n2 - n1));
                }
            }
            return Integer.valueOf(stack.pop());
        }

        private boolean isNum(char c) {
            return c >= '0' && c <= '9';
        }

        private boolean isOperator(char c) {
            return isPlusMinus(c) || isMulDiv(c);
        }

        private boolean isPlusMinus(char c) {
            return c == '+' || c == '-';
        }

        private boolean isMulDiv(char c) {
            return c == '*' || c == '/';
        }
    }

    class Solution33 {
        public int search(int[] nums, int target) {
            return search(nums, 0, nums.length - 1, target);
        }

        private int search(int[] nums, int start, int end, int target) {
            if (start > end) {
                return -1;
            }
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] > target) { // start .. mid or mid .. end
                if (nums[mid] >= nums[start] && nums[start] <= target) {
                    return search(nums, start, mid - 1, target);
                } else {
                    return search(nums, mid + 1, end, target);
                }
            }
            if (nums[mid] < target) {
                if (nums[end] >= nums[mid] && nums[end] >= target) {
                    return search(nums, mid + 1, end, target);
                } else {
                    return search(nums, start, mid - 1, target);
                }
            }
            return -1;
        }
    }

    class Solution76 {
        public String minWindow(String s, String t) {
            Map<Character, Integer> expected = new HashMap<>();
            Map<Character, Integer> existed = new HashMap<>();
            for (int i = 0; i < t.length(); i++) {
                char c = t.charAt(i);
                expected.putIfAbsent(c, 0);
                expected.put(c, expected.get(c) + 1);
                existed.putIfAbsent(c, 0);
            }
            int start = -1;
            String result = null;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (expected.containsKey(c)) {
                    if (start == -1) {
                        start = i;
                    }
                    existed.put(c, existed.get(c) + 1);
                    while (isMatch(expected, existed)) {
                        if (result == null || i - start + 1 < result.length()) {
                            result = s.substring(start, i + 1);
                        }
                        char cAtStart = s.charAt(start);
                        existed.put(cAtStart, existed.get(cAtStart) - 1);
                        for (int j = start + 1; j <= i; j++) {
                            char cc = s.charAt(j);
                            if (expected.containsKey(cc)) {
                                start = j;
                                break;
                            }
                        }
                    }
                }
            }
            return result == null ? "" : result;
        }

        private boolean isMatch(Map<Character, Integer> expected, Map<Character, Integer> existed) {
            for (char c : expected.keySet()) {
                if (existed.get(c) < expected.get(c)) {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution84 {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int[] firstSmallerAtRight = new int[n];
            Stack<Integer> stack = new Stack<>();
            int result = 0;
            for (int i = 0; i <= n; i++) {
                int h = 0;
                if (i == n) {
                    h = 0;
                } else {
                    h = heights[i];
                }
                while (!stack.isEmpty() && heights[stack.peek()] > h) {
                    int idx = stack.pop();
                    firstSmallerAtRight[idx] = i;
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    result = Math.max(heights[idx] * (i - left - 1), result);
                }
                stack.push(i);
            }
            return result;
        }
    }

    class Solution84_1 {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int[] firstSmallerAtRight = new int[n];
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < n; i++) {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    int idx = stack.pop();
                    firstSmallerAtRight[idx] = i;
                }
                stack.push(i);
            }
            while (!stack.isEmpty()) {
                int idx = stack.pop();
                firstSmallerAtRight[idx] = n;
            }

            int[] firstSmallerAtLeft = new int[n];
            stack = new Stack<>();
            for (int i = n - 1; i >= 0; i--) {
                while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                    int idx = stack.pop();
                    firstSmallerAtLeft[idx] = i;
                }
                stack.push(i);
            }
            while (!stack.isEmpty()) {
                int idx = stack.pop();
                firstSmallerAtLeft[idx] = -1;
            }
            int result = 0;
            for (int i = 0; i < n; i++) {
                result = Math.max(result,
                        heights[i] * (firstSmallerAtRight[i] - firstSmallerAtLeft[i] - 1));
            }
            return result;
        }
    }

    class Solution567 {
        public boolean checkInclusion(String s1, String s2) {
            int start = -1;
            Map<Character, Integer> expected = new HashMap<>();
            for (char c : s1.toCharArray()) {
                expected.putIfAbsent(c, 0);
                expected.put(c, expected.get(c) + 1);
            }
            Map<Character, Integer> copy = new HashMap<>(expected);

            for (int i = 0; i < s2.length(); i++) {
                char c = s2.charAt(i);
                if (expected.containsKey(c)) {
                    if (start == -1) {
                        start = i;
                    }
                    if (expected.get(c) > 0) {
                        expected.put(c, expected.get(c) - 1);
                        int sum = 0;
                        for (int v : expected.values()) {
                            sum += v;
                        }
                        if (sum == 0) {
                            return true;
                        }
                    } else {
                        for (int j = start; j < i; j++) {
                            char cc = s2.charAt(j);
                            if (cc == c) {
                                start = j + 1;
                                break;
                            }
                            expected.put(cc, expected.get(cc) + 1);
                        }
                    }
                } else {
                    start = -1;
                    expected = new HashMap<>(copy);
                }
            }
            return false;
        }
    }

    class Solution139 {
        public boolean wordBreak(String s, List<String> wordDict) {
            boolean[] dp = new boolean[s.length()];
            for (int i = 0; i < s.length(); i++) {
                for (String w : wordDict) {
                    int startIdx = i + 1 - w.length();
                    if (i + 1 - w.length() >= 0 &&
                            s.substring(i + 1 - w.length(), i + 1).equals(w)) {
                        if (i + 1 - w.length() == 0) {
                            dp[i] = true;
                        }
                        if (i + 1 - w.length() > 0) {
                            dp[i] = dp[i] || dp[i + 1 - w.length() - 1];
                        }
                        if (dp[i]) {
                            break;
                        }
                    }
                }
            }
            return dp[s.length() - 1];
        }
    }

    class Solution207 {
        public boolean canFinish(int numCourses, int[][] prerequisites) {
            Map<Integer, Set<Integer>> requirement = new HashMap<>();
            for (int i = 0; i < numCourses; i++) {
                requirement.putIfAbsent(i, new HashSet<>());
            }
            for (int i = 0; i < prerequisites.length; i++) {
                requirement.get(prerequisites[i][0]).add(prerequisites[i][1]);
            }
            int finishCount = 0;
            Set<Integer> finished = new HashSet<>();
            for (int i = 0; i < numCourses; i++) {
                if (requirement.get(i).isEmpty()) {
                    finished.add(i);
                    finishCount++;
                    requirement.remove(i);
                }
            }
            while (finished.size() != 0) {
                Set<Integer> newFinished = new HashSet<>();
                for (int c : finished) {
                    for (int i : requirement.keySet()) {
                        if (newFinished.contains(i)) {
                            continue;
                        }
                        requirement.get(i).remove(c);
                        if (requirement.get(i).isEmpty()) {
                            newFinished.add(i);
                            finishCount++;
                        }
                    }
                }
                for (int c : newFinished) {
                    requirement.remove(c);
                }
                finished = newFinished;
            }
            return finishCount == numCourses;
        }
    }
}
