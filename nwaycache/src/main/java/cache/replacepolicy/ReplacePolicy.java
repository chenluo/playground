package cache.replacepolicy;

import cache.CacheEntry;

public interface ReplacePolicy {
    public void dropOne(CacheEntry entry);
}
