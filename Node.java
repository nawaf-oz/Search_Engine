public class Node<T> {
    public T Data;
    public Node<T> Next;
    public Node() {
	Data = null;
	Next = null;
	}
    public Node (T val) {
        Data=val;
        Next=null;
    }
}
