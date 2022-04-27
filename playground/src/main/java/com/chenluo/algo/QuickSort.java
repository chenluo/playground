package com.chenluo.algo;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuickSort {
    private static long seed = 19900928;
    private static Random random = new Random(seed);

    public static void main(String[] args) {
        random.nextInt();
        Random random = new Random(19900928);
        for (int k : new int[]{10, 100, 10000, 100000000}) {
            List<Integer> unsortedList = new ArrayList<>(k);
            for (int i = 0; i < k; i++) {
                unsortedList.add(random.nextInt());
            }
            List<Integer> sortedList = new ArrayList<>(unsortedList);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Collections.sort(sortedList);
            stopWatch.stop();
            System.out.println("Collections.sort takes:" + stopWatch.getTime(TimeUnit.SECONDS));
            stopWatch = new StopWatch();
            stopWatch.start();
            QuickSort.sort(unsortedList, 0, unsortedList.size());
            stopWatch.stop();
            System.out.println("quickSort takes:" + stopWatch.getTime(TimeUnit.SECONDS));
            for (int i = 0; i < k; i++) {
                if (!sortedList.get(i).equals(unsortedList.get(i))) {
                    System.out.println("sort result error. k=" + k);
                    break;
                }
            }
        }
    }


    public static <T extends Comparable<? super T>> void sort(List<T> unsorted,
                                                              int start/*inclusive*/, int end /*exclusive*/) {
        if (start >= end) {
            return;
        }
        int pivotIdx = partition(unsorted, start, end);
        sort(unsorted, start, pivotIdx);
        sort(unsorted, pivotIdx + 1, end);
    }


    private static <T extends Comparable<? super T>> int partition(List<T> unsorted, int start, int end) {
        if (start >= end) {
            return start;
        }
        // pivot 选择，固定最前，最后，随机3种, 需要把pivot放到最后一个位置，方便移动。
        T pivotValue = unsorted.get(end - 1);
        int small = 0; // how many number is smaller than the pivot

        for (int i = start; i < end; i++) {
            if (unsorted.get(i).compareTo(pivotValue) < 0) {
                swap(unsorted, i, start + small);
                small++;
            }
        }
        swap(unsorted, start + small, end - 1);
        return start + small;
    }

    private static <T extends Comparable<? super T>> void swap(List<T> unsorted, int idx1, int idx2) {
        T temp = unsorted.get(idx1);
        unsorted.set(idx1, unsorted.get(idx2));
        unsorted.set(idx2, temp);
    }
}
