package Cache;

import DataStructures.CacheNode;
import Interfaces.CacheValue;
import Exception.KeyNotFoundException;

import java.util.concurrent.ConcurrentHashMap;

public class LRUCache <K, V extends CacheValue>
{
    private ConcurrentHashMap<K, CacheNode<K,V>> dict;
    private CacheNode<K, V> head ;
    private CacheNode<K, V> tail ;
    private int maxCacheSize;
    private int usedCacheSize;
    public LRUCache(int cacheSize)
    {
        dict = new ConcurrentHashMap<K, CacheNode<K, V>>();
        head = new CacheNode<K, V>();
        tail = new CacheNode<K, V>();
        head.setPreviousNode(tail);
        tail.setNextNode(head);
        this.maxCacheSize = cacheSize;
    }

    public synchronized void Add(K key, V value)
    {
        this.usedCacheSize += value.getSize();
        if(this.usedCacheSize >= this.maxCacheSize)
        {
            freeUpSomeSpace(this.usedCacheSize - this.maxCacheSize);
        }
        CacheNode<K, V> newNode = new CacheNode<K, V>(key, value);
        moveNodeToFront(newNode);
        this.dict.put(key, newNode);
    }

    public int getCurrentCacheSize()
    {
        return usedCacheSize;
    }

    public int getCacheItemsCount()
    {
        return dict.size();
    }

    public synchronized V get(K key)
    {
        if(this.dict.containsKey(key))
        {
            CacheNode<K, V> node = this.dict.get(key);
            // this is the last accessed node move it to the front
            moveNodeToFront(node);
            return node.getValue();
        }
        return null;
    }

    // two threads cannot modify the linked list at the same time
    private void moveNodeToFront(CacheNode<K, V> node) {
        // synchronize this operation. Multiple threads cannot change the header
        if (node != null) {
            // move this to the front of the linked list

            if(isNewNode(node)) {
                modeNewNodeToFront(node);
            }
            else {
                moveExistingNodeToFront(node);
            }
        }
    }

    private void moveExistingNodeToFront(CacheNode<K, V> node) {
        CacheNode<K, V> currentNodesTailNode = node.getPreviousNode();
        CacheNode<K, V> currentNodesHeadNode = node.getNextNode();

        // remove the current node from between
        currentNodesHeadNode.setPreviousNode(currentNodesTailNode);
        currentNodesTailNode.setNextNode(currentNodesHeadNode);

        // insert it between the HEAD and its previous node
        CacheNode<K, V> headsTailNode = head.getPreviousNode();
        headsTailNode.setNextNode(node);
        node.setPreviousNode(headsTailNode);
        node.setNextNode(head);
        head.setPreviousNode(node);
    }

    private void modeNewNodeToFront(CacheNode<K, V> node) {
        CacheNode<K, V> prevNode = head.getPreviousNode();
        node.setNextNode(head);
        head.setPreviousNode(node);
        prevNode.setNextNode(node);
        node.setPreviousNode(prevNode);
    }

    private boolean isNewNode(CacheNode<K,V> node) {
        return node.getNextNode() == null;
    }

    // two threads cannot try to modify the linked list again at the same time
    private void freeUpSomeSpace(int minSizeToFree)
    {
        // start deleting the instances from the tail until the size are freeup
        int freedUpSize = 0;
        while (freedUpSize < minSizeToFree)
        {
            // start from the tail node
            CacheNode<K,V> nodeToFree = tail.getNextNode();
            freedUpSize += nodeToFree.getValue().getSize();

            CacheNode<K,V> nextNode = nodeToFree.getNextNode();
            tail.setNextNode(nextNode);
            nextNode.setPreviousNode(tail);
            // remove the referenecs and also delete them from the dictionary
            K key = nodeToFree.getKey();
            this.usedCacheSize -= nodeToFree.getValue().getSize();
            dict.remove(key);
        }
    }
}
