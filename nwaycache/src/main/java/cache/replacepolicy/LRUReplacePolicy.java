package cache.replacepolicy;

import cache.CacheEntry;

public class LRUReplacePolicy implements ReplacePolicy {
    @Override
    public void dropOne(CacheEntry head) {
        CacheEntry prev = head;
        CacheEntry targetPrev = null;
        head = head.next;
        int smallestTerm = Integer.MAX_VALUE;
        while (head != null) {
            if (head.term < smallestTerm) {
                smallestTerm = head.term;
                targetPrev = prev;
            }
            prev = head;
            head = head.next;
        }

        if (targetPrev != null) {
            targetPrev.next = targetPrev.next.next;
        }

    }
}
