package Cahe;

import Cache.LRUCache;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class TestLRUCache {
    @Test
    public void TestLRUCache() {
        int cacheSize = 200; //200 bytes
        LRUCache<Integer, Person> lruCache = new LRUCache<Integer, Person>(cacheSize);

        Person p = new Person();
        int i = 0;
        int dataSize = 0;
        while (lruCache.getCurrentCacheSize() + p.getSize() < cacheSize) {
            // keep on adding item to the caches till it fills up
            p = getTestPersonName(i++);
            lruCache.Add(p.getId(), p);
            dataSize += p.getSize();
        }

        // most likely now you can add ony one item to the cache
        Assert.assertEquals(dataSize, lruCache.getCurrentCacheSize());

        // try to add another item , cache should remove the first time that was added
        p = getTestPersonName(i++);
        lruCache.Add(p.getId(), p);

        // check that the first item was removed
        Assert.assertEquals(null, lruCache.get(0));

        Assertions.assertEquals(1, 1);

        // now access the last item in the cache which is 1. This should go to the front
        lruCache.get(1);

        // Add another item. This should remove key 2 and 1 should stay active
        p = getTestPersonName(i++);
        lruCache.Add(p.getId(), p);

        Assert.assertNull(lruCache.get(2));
        Assert.assertNotNull(lruCache.get(1));
    }
    private Person getTestPersonName(int i)
    {
        Person p = new Person();
        p.setId(i);
        p.setFirstName("Hariom"+i);
        p.setLastName("Kuntal"+i);
        return p;
    }
}
