package DataStructures;

public class Node<T> {

    private T data;

    public Node()
    {}

    public Node(T data)
    {
        this.data = data;
    }

    public T getValue()
    {
        return this.data;
    }
}
