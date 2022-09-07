package cache.replacepolicy;

import cache.CacheEntry;

import javax.swing.plaf.SplitPaneUI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class LRUReplacePolicy<K> implements ReplacePolicy<K> {
    Node<K> head; // dummy head
    Node<K> tail; // dummy tail
    Set<K> set;
    int capacity;

    public LRUReplacePolicy(int capacity) {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;
        set = new LinkedHashSet<>(capacity);
        this.capacity = capacity;
    }

    @Override
    public K tryPutKey(K key) {
        K removedKey = null;
        set.remove(key);
        if (set.size() == capacity) {
            removedKey = set.iterator().next();
            set.remove(removedKey);
        }
        set.add(key);
        return removedKey;
    }

    private static class Node<K> {
        K key;
        Node<K> prev;
        Node<K> next;

        public Node() {
            key = null;
            prev = null;
            next = null;
        }

        public Node(K key) {
            this.key = key;
            this.prev = null;
            this.next = null;
        }
    }
}
