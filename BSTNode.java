public class BSTNode<T> {
    public String Key;
    public T Data;
    public BSTNode<T>Left, Right;
    public BSTNode(String key, T data) {
        this.Key = key;
        this.Data = data;
        Left = Right = null;
    }
}
