public class BST<T> {
    private BSTNode<T> Root, Current;
    public BST() {
        Current = Root = null;
    }
    public boolean Empty() {
        return Root == null;
    }
    public boolean Full() {
        return false;
    }
    public T Retrieve() {
        return Current.Data;
    }
    public boolean FindKey(String K) {
        BSTNode<T> p = Root;
        while (p != null) {
            Current = p;
            if (K.compareToIgnoreCase(p.Key) == 0) {
                return true;
            } else if (K.compareToIgnoreCase(p.Key) < 0) {
                p = p.Left;
            } else {
                p = p.Right;
            }
        }
        return false;
    }
    public boolean Insert(String K, T Val) {
        if (Root == null) {
            Current = Root = new BSTNode<T>(K, Val);
            return true;
        }
        BSTNode<T> p = Current;
        if (FindKey(K)) {
            Current = p;
            return false;
        }
        BSTNode<T> tmp = new BSTNode<T>(K, Val);
        if (K.compareToIgnoreCase(Current.Key) < 0) {
            Current.Left = tmp;
        } else {
            Current.Right = tmp;
        }
        Current = tmp;
        return true;
    }
    public void InOrder() {
        if (Root == null)
            System.out.println("empty tree");
        else
            InOrder(Root);
    }
    private void InOrder(BSTNode<T> P) {
        if (P == null)
            return;
        InOrder(P.Left);
       ((Word)P.Data).display();
        InOrder(P.Right);
    }
    }
