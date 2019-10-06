package DataStructures;

public class CacheNode<K, T> extends Node<T>{

    private CacheNode<K, T> prev;
    private CacheNode<K, T> next;
    private K key;

    public CacheNode()
    {

    }

    public CacheNode(K key, T value)
    {
        super(value);
        this.key = key;
    }
    public CacheNode<K, T> getPreviousNode()
    {
        return prev;
    }

    public CacheNode<K, T> getNextNode()
    {
        return next;
    }

    public K getKey()
    {
        return key;
    }
    public void setPreviousNode(CacheNode<K, T> node)
    {
        this.prev = node;
    }

    public void setNextNode(CacheNode<K, T> node)
    {
        this.next = node;
    }

}
