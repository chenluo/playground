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
    public void testLinkedHashSet() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");

        set.remove("2");
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

    @Test
    public void Q114() {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        node1.left = node2;
        node1.right = node5;
        node2.left = node3;
        node2.right = node4;
        node5.right = node6;

        new Solution114().flatten(node1);
        System.out.println(node1.val);
    }

    @Test
    public void Q239() {
        //        System.out.println( Arrays.toString(new Solution239().maxSlidingWindow(new
        //        int[]{1, 2, 3, 4, 5}, 3)));
        System.out.println(Arrays.toString(
                new Solution239().maxSlidingWindow(new int[]{3, 3, 3, 1, 3, 3, 3, 3, 3, 3}, 3)));
    }

    @Test
    public void Q43() {
        System.out.println(new Solution43().multiply("111111", "1"));
    }

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(1);
        queue.add(2);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        queue.add(2);
        queue.add(1);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(1);
        maxHeap.add(2);
        maxHeap.add(3);
        maxHeap.add(4);
        maxHeap.add(5);
        System.out.println(maxHeap.poll());

        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(it -> it[0]));

    }

    @Test
    public void Q97() {
        //        Assertions.assertFalse(new Solution97().isInterleave("", "", "s"));
    }

    @Test
    public void q402() {
        //        Assertions.assertEquals(new Q402().removeKdigits("1234567", 1), "123456");
        //        Assertions.assertEquals(new Q402().removeKdigits("1234561", 1), "123451");
        //        Assertions.assertEquals(new Q402().removeKdigits("1232561", 1), "122561");
        Assertions.assertEquals(new Q402().removeKdigits("123256", 6), "0");
        Assertions.assertEquals(new Q402().removeKdigits("123256", 5), "1");
    }

    @Test
    public void testEncoding() {
        String s = new String(new byte[]{0, 1, 2, 19, 1, 127});
        System.out.println(s);
    }

    @Test
    public void testLinkedListAsStack() {
        Deque<String> l = new ArrayDeque<String>();
        l.add("1");
        l.add("2");
        l.add("3");
        System.out.println(l.peek());
        System.out.println(l.peekFirst());
        System.out.println(l.peekLast());
        System.out.println(l.poll());
        System.out.println(l.pollLast());
        System.out.println(l.peek());
    }

    @Test
    public void Q2276() {
        CountIntervals q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(7, 10);
        Assertions.assertEquals(6, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(3, 10);
        Assertions.assertEquals(9, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(4, 10);
        Assertions.assertEquals(9, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(1, 10);
        Assertions.assertEquals(10, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(1, 2);
        Assertions.assertEquals(3, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(5, 6);
        Assertions.assertEquals(6, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(5, 5);
        Assertions.assertEquals(5, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(3, 4);
        Assertions.assertEquals(5, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(6, 10);
        Assertions.assertEquals(7, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(3, 10);
        Assertions.assertEquals(9, q.count());

        q = new CountIntervals();
        q.add(2, 3);
        Assertions.assertEquals(2, q.count());
        q.add(9, 10);
        Assertions.assertEquals(4, q.count());
        q.add(3, 11);
        Assertions.assertEquals(10, q.count());

        q = new CountIntervals();
        q.add(571, 770);
        Assertions.assertEquals(200, q.count);
        q.add(920, 996);
        Assertions.assertEquals(277, q.count);
        q.add(65, 512);
        Assertions.assertEquals(725, q.count);
        q.add(959, 959);
        Assertions.assertEquals(725, q.count);
        q.add(313, 330);
        Assertions.assertEquals(725, q.count);
        q.add(473, 928);
        Assertions.assertEquals(932, q.count);
        q.add(75, 561);
        Assertions.assertEquals(932, q.count);
        //        q.add(107,835);
        //        Assertions.assertEquals();
        //        q.add(852,918);
        //        Assertions.assertEquals();
        //        q.add(12,774);
        //        Assertions.assertEquals();
        //        q.add(534,597);
        //        Assertions.assertEquals();
        //        q.add(743,776);
        //        Assertions.assertEquals();
        //        q.add(456,556);
        //        Assertions.assertEquals();
        //        q.add(727,750);
        //        Assertions.assertEquals();
        //        q.add(403,954);
        //        Assertions.assertEquals();

    }

    @Test
    public void Q750() {
        Q750NumberOfCornelRectangle solver = new Q750NumberOfCornelRectangle();
        Assertions.assertEquals(1, solver.numberOfCornelRectangle(
                new int[][]{{1, 0, 0, 1, 0}, {0, 0, 1, 0, 1}, {0, 0, 0, 1, 0}, {1, 0, 1, 0, 1}}));
        Assertions.assertEquals(0, solver.numberOfCornelRectangle(new int[][]{{1, 1, 1, 1, 1}}));
        Assertions.assertEquals(10,
                solver.numberOfCornelRectangle(new int[][]{{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}}));
        Assertions.assertEquals(6,
                solver.numberOfCornelRectangle(new int[][]{{1, 1, 1, 1, 1}, {1, 0, 1, 1, 1}}));
        Assertions.assertEquals(1, solver.numberOfCornelRectangleHashMap(
                new int[][]{{1, 0, 0, 1, 0}, {0, 0, 1, 0, 1}, {0, 0, 0, 1, 0}, {1, 0, 1, 0, 1}}));
        Assertions.assertEquals(0,
                solver.numberOfCornelRectangleHashMap(new int[][]{{1, 1, 1, 1, 1}}));
        Assertions.assertEquals(10, solver.numberOfCornelRectangleHashMap(
                new int[][]{{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}}));
        Assertions.assertEquals(6, solver.numberOfCornelRectangleHashMap(
                new int[][]{{1, 1, 1, 1, 1}, {1, 0, 1, 1, 1}}));
    }

    @Test
    public void Q1671() {
        //        Assertions.assertEquals(1, new Q1671().minimumMountainRemovals(new int[]{23,
        //        47, 63, 72, 81, 99, 88, 55, 21, 33, 32}));
        Assertions.assertEquals(6, new Q1671().minimumMountainRemovals(
                new int[]{100, 92, 89, 77, 74, 66, 64, 66, 64}));

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

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Solution114 {
        public void flatten(TreeNode root) {
            helper(root);
        }

        private TreeNode helper(TreeNode node) {
            if (node == null) {
                return null;
            }
            TreeNode right = node.right;
            node.right = null;
            if (node.left != null) {
                node.right = helper(node.left);
            }
            node.left = null;
            TreeNode temp = node;
            while (temp != null && temp.right != null) {
                temp = temp.right;
            }
            if (right != null) {
                temp.right = helper(right);
            }
            return node;
        }
    }

    class Solution239 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            List<Integer> numInWindow = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                int idx = numInWindow.size();
                for (int j = 0; j < numInWindow.size(); j++) {
                    if (numInWindow.get(j) > nums[i]) {
                        idx = j;
                        break;
                    }
                }
                numInWindow.add(idx, nums[i]);
            }
            int[] result = new int[nums.length - k + 1];
            result[0] = numInWindow.get(k - 1);
            for (int i = k; i < nums.length; i++) {
                int nToRemove = nums[i - k];
                int nToAdd = nums[i];
                int idxToRemove = -1;
                int idxToAdd = -1;
                if (nToRemove == nToAdd) {
                    result[i - k + 1] = numInWindow.get(k - 1);
                    continue;
                }
                for (int j = 0; j < k; j++) {
                    if (idxToRemove == -1 && numInWindow.get(j) == nToRemove) {
                        idxToRemove = j;
                    }
                    if (idxToAdd == -1 && numInWindow.get(j) > nToAdd) {
                        idxToAdd = j;
                    }
                }
                if (idxToAdd == -1) {
                    idxToAdd = k;
                }
                numInWindow.remove(idxToRemove);
                if (idxToRemove < idxToAdd) {
                    idxToAdd--;
                }
                numInWindow.add(idxToAdd, nToAdd);
                result[i - k + 1] = numInWindow.get(k - 1);
            }
            return result;
        }
    }

    class Solution43 {
        public String multiply(String num1, String num2) {
            int length = 5;
            String result = "0";
            for (int i = 0; i < num1.length(); ) {
                int num1End = i + length <= num1.length() ? i + length : num1.length();
                int n1 = Integer.valueOf(num1.substring(i, num1End));
                for (int j = 0; j < num2.length(); ) {
                    int num2End = j + length <= num2.length() ? j + length : num2.length();
                    int n2 = Integer.valueOf(num2.substring(j, num2End));
                    int tmp = n1 * n2;
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < num1.length() - num1End + num2.length() - num2End; k++) {
                        sb.append("0");
                    }
                    result = add(result, String.valueOf(tmp) + sb.toString());
                    j += length;
                }
                i += length;
            }
            return result;
        }

        private String add(String num1, String num2) {
            int idx1 = num1.length() - 1;
            int idx2 = num2.length() - 1;
            int length = Math.max(idx1, idx2) + 1;
            int[] ret = new int[length + 1];
            int idx = length;
            int flag = 0;
            while (idx >= 0) {
                int n1 = idx1 >= 0 ? num1.charAt(idx1) - '0' : 0;
                int n2 = idx2 >= 0 ? num2.charAt(idx2) - '0' : 0;

                int tmp = n1 + n2 + flag;
                flag = 0;
                if (tmp > 9) {
                    tmp = tmp % 10;
                    flag = 1;
                }
                ret[idx] = tmp;
                idx--;
                idx1--;
                idx2--;
            }
            StringBuilder sb = new StringBuilder();
            idx = 0;
            while (idx < length && ret[idx] == 0) {
                idx++;
            }
            for (int i = idx; i <= length; i++) {
                sb.append(ret[i]);
            }
            String result = sb.toString();
            return result.equals("") ? "0" : result;

        }
    }

    class Solution97 {
        // dp[i][j] : true s1[0..i] and s2[0..j] interleave s3[0..i+j]
        // dp[i][j] = d[i-1][j] if s1[i] == s3[i+j]
        //          = d[i][j-1] if s2[j] == s3[i+j]
        // dp[0][j] = true if s2[0..j] == s3[0..j]
        // dp[i][0] = true if s1[0..i] == s3[0..i]
        public boolean isInterleave(String s1, String s2, String s3) {
            boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
            for (int i = 0; i <= s1.length(); i++) {
                for (int j = 0; j <= s2.length(); j++) {
                    if (j == 0) {
                        dp[i][j] = true;
                        continue;
                    }
                    dp[i][j] =
                            (i > 0 && s1.charAt(i - 1) == s3.charAt(i + j - 1) && dp[i - 1][j]) ||
                                    (j > 0 && s2.charAt(j - 1) == s3.charAt(i + j - 1) &&
                                            dp[i][j - 1]);
                }
            }
            return dp[s1.length()][s2.length()];
        }
    }

    class Q402 {
        public String removeKdigits(String num, int k) {
            Deque<Character> stack = new ArrayDeque<>();
            for (int i = 0; i < num.length(); i++) {
                while (k > 0 && !stack.isEmpty() && stack.peekLast() > num.charAt(i)) {
                    stack.pollLast();
                    k--;
                }
                stack.addLast(num.charAt(i));
            }

            while (k > 0) {
                stack.pollLast();
                k--;
            }

            if (stack.isEmpty()) {
                return "0";
            }

            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty() && stack.peekFirst().equals('0')) {
                stack.pollFirst();
            }
            while (!stack.isEmpty()) {
                sb.append(stack.pollFirst());
            }
            return sb.toString();
        }
    }

    class CountIntervals {
        List<int[]> ranges = new ArrayList<>();
        int count = 0;

        public CountIntervals() {

        }

        public void add(int left, int right) {
            int l = 0;
            int r = ranges.size() - 1;
            while (l <= r) {
                int m = l + (r - l) / 2;
                int[] temp = ranges.get(m);
                if (temp[0] == left) {
                    l = m;
                    break;
                }
                if (temp[0] > left) {
                    r = m - 1;
                }
                if (temp[0] < left) {
                    l = m + 1;
                }
            }
            int[] current = {left, right};
            ranges.add(l, current);
            // prev current next
            int[] prev = l - 1 >= 0 ? ranges.get(l - 1) : null;
            int[] next = l + 1 < ranges.size() ? ranges.get(l + 1) : null;
            if (prev != null) {
                if (prev[1] + 1 >= current[0]) {
                    current[0] = prev[0];
                    current[1] = Math.max(current[1], prev[1]);
                    ranges.remove(l - 1);
                    count -= prev[1] - prev[0] + 1;
                    l--;
                }
            }
            while (next != null && current[1] + 1 > next[0]) {
                current[1] = Math.max(current[1], next[1]);
                ranges.remove(l + 1);
                count -= next[1] - next[0] + 1;
                next = null;
                if (l + 1 < ranges.size()) {
                    next = ranges.get(l + 1);
                }
            }
            count += current[1] - current[0] + 1;
        }

        public int count() {
            return count;
        }
    }

    class Q750NumberOfCornelRectangle {
        public int numberOfCornelRectangle(int[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            // dp[i][j] i, j means column,
            // for each row update dp[i][j]
            // dp[i][j] +=1, if matrix[r][i] == matrix[r][j],
            // ==> row*col^2
            // at last row, if matrix[row][i] == matrix[row][j] == 1, count+=dp[i][j]
            /**
             * matrix:
             * [[10010],
             *  [00101],
             *  [00010],
             *  [10101]]
             * dp:
             * 1st row:
             * 00010
             * 00000
             * 00000
             * 00000
             * 00000
             * 2nd row:
             * 00010
             * 00000
             * 00001
             * 00000
             * 00000
             * 3rd row:
             * 00010
             * 00000
             * 00001
             * 00000
             * 00000
             * 4th row:
             * 00111
             * 00000
             * 00002
             * 00000
             * 00000
             **/
            int[][] dp = new int[col][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    for (int k = j + 1; k < col; k++) {
                        if (matrix[i][j] == 1 && matrix[i][k] == 1) {
                            dp[j][k]++;
                        }
                    }
                }
            }
            int result = 0;
            for (int i = 0; i < col; i++) {
                for (int j = 0; j < col; j++) {
                    if (dp[i][j] >= 2) {
                        result += dp[i][j] - 1;
                    }
                }
            }
            return result;
        }

        public int numberOfCornelRectangleHashMap(int[][] matrix) {
            int row = matrix.length;
            int col = matrix[0].length;
            HashMap<String, Integer> map = new HashMap<>();
            int result = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    for (int k = j + 1; k < col; k++) {
                        if (matrix[i][j] == 1 && matrix[i][k] == 1) {
                            String key = j + "," + k;
                            map.compute(key, (kk, vv) -> vv == null ? 1 : vv + 1);
                            result += map.get(key) - 1;
                        }
                    }
                }
            }
            return result;
        }
    }

    class Q1671 {
        public int minimumMountainRemovals(int[] nums) {
            // l2r[i]: longest increasing sequence end at i from 0
            // r2l[i]: longest increasing sequence end at i from nums.length-1
            int[] l2r = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                l2r[i] = 1;
            }
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) {
                        l2r[i] = Math.max(l2r[i], l2r[j] + 1);
                    }
                }
            }

            int[] r2l = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                r2l[i] = 1;
            }
            for (int i = nums.length - 2; i >= 0; i--) {
                for (int j = nums.length - 1; j > i; j--) {
                    if (nums[i] > nums[j]) {
                        r2l[i] = Math.max(r2l[i], r2l[j] + 1);
                    }
                }
            }
            int result = nums.length;
            for (int i = 1; i < nums.length - 1; i++) {
                if (l2r[i] > 1 && r2l[i] > 1) { // i is valid peak
                    result = Math.min(nums.length + 1 - (l2r[i] + r2l[i]), result);
                }
            }
            return result;
            // stack solution WRONG SOLUTION:
            //            // from left to right, constructing a strict increasing stack, record
            //            dropped elements
            //            // and then go from right to left
            //
            //            // find the minimum l2r[i] + r2l[i] and return
            //            int[] l2r = new int[nums.length];
            //            Stack<Integer> stack = new Stack<>();
            //            int dropped = 0;
            //            for (int i = 0; i < nums.length; i++) {
            //                int num = nums[i];
            //                while (!stack.isEmpty() && stack.peek() >= num) {
            //                    stack.pop();
            //                    dropped++;
            //                }
            //                l2r[i] = dropped;
            //                stack.push(num);
            //            }
            //
            //            int[] r2l = new int[nums.length];
            //            stack.clear();
            //            dropped = 0;
            //            for (int i = nums.length - 1; i >= 0; i--) {
            //                int num = nums[i];
            //                while (!stack.isEmpty() && stack.peek() >= num) {
            //                    stack.pop();
            //                    dropped++;
            //                }
            //                r2l[i] = dropped;
            //                stack.push(num);
            //            }
            //            int result = nums.length;
            //            for (int i = 0; i < nums.length; i++) {
            //                result = Math.min(result, l2r[i] + r2l[i]);
            //            }
            //            return result;
        }
    }

    @Test
    public void Q25() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        ListNode n7 = new ListNode(7);
        ListNode n8 = new ListNode(8);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        n6.next = n7;
        n7.next = n8;
        new Q25Solution().reverseKGroup(n1, 1);
        //        new Q25Solution().reverseKGroup(n1, 2);
        //        new Q25Solution().reverseKGroup(n1, 3);
    }

    class Q25Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            return reverse(head, k);
        }

        private ListNode reverse(ListNode start, int k) {
            if (start == null) {
                return null;
            }
            ListNode kth = start;
            int kk = k - 1;
            while (kth != null && kk > 0) {
                kth = kth.next;
                kk--;
            }
            if (kth == null) {
                return start;
            }
            ListNode prev = null;
            ListNode current = start;
            ListNode nextStart = kth.next;
            kth.next = null;
            while (current != null) {
                ListNode tmp = current.next;
                current.next = prev;
                prev = current;
                current = tmp;
            }
            start.next = reverse(nextStart, k);
            return kth;
        }
    }

    @Test
    public void Q707() {
        MyLinkedList l = new MyLinkedList();
        l.addAtHead(7);
        l.addAtHead(2);
        l.addAtHead(1);
        l.addAtIndex(3, 0);
        l.deleteAtIndex(2);
        l.addAtHead(6);
        l.addAtTail(4);
        l.get(4);
        l.addAtHead(4);
        l.addAtIndex(5, 0);
        l.addAtHead(6);


    }

    class MyLinkedList {
        int size = 0;
        Node head = null;
        Node tail = null;


        public MyLinkedList() {
            head = new Node();
            tail = head;
        }

        public int get(int index) {
            if (index < size) {
                Node tmp = head.next;
                for (int i = 0; i < index; i++) {
                    tmp = tmp.next;
                }
                return tmp.val;
            }
            return -1;
        }

        private Node getNode(int index) {
            if (index < size) {
                Node tmp = head.next;
                for (int i = 0; i < index; i++) {
                    tmp = tmp.next;
                }
                return tmp;
            }
            return null;
        }

        public void addAtHead(int val) {
            Node node = new Node(val);
            size++;
            Node tmp = head.next; // old first node
            head.next = node;
            node.prev = head;
            node.next = tmp;
            if (tmp == null) {
                // add into an empty list
                tail = node;
            } else {
                tmp.prev = node;
            }
        }

        public void addAtTail(int val) {
            size++;
            Node node = new Node(val);
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        public void addAtIndex(int index, int val) {
            if (index == 0) {
                addAtHead(val);
            } else if (index == size) {
                addAtTail(val);
            } else if (index < size) {
                Node tmp = getNode(index);
                // new node -> tmp
                Node node = new Node(val);
                node.prev = tmp.prev;
                tmp.prev.next = node;
                node.next = tmp;
                tmp.prev = node;
                size++;
            }

        }

        public void deleteAtIndex(int index) {
            Node tmp = getNode(index);
            if (tmp == null) {
                return;
            }
            Node prev = tmp.prev;
            Node next = tmp.next;
            prev.next = next;
            if (next == null) {
                // removing tail node
                tail = prev;
            } else {
                next.prev = prev;
            }
            size--;
        }
    }

    class Node {
        int val;
        Node next;
        Node prev;

        public Node() {
            val = 0;
            next = null;
            prev = null;
        }

        public Node(int val) {
            this.val = val;
            next = null;
            prev = null;
        }
    }

    /**
     * Your MyLinkedList object will be instantiated and called as such:
     * MyLinkedList obj = new MyLinkedList();
     * int param_1 = obj.get(index);
     * obj.addAtHead(val);
     * obj.addAtTail(val);
     * obj.addAtIndex(index,val);
     * obj.deleteAtIndex(index);
     */
}
