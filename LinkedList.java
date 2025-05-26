public class LinkedList<T> implements List<T> {
    private Node<T> Head;
    private Node<T> Current;
    int Num;
    public LinkedList() {
        Head=Current=null;
    }
    public void findFirst() {
        Current=Head;
    }
    public void findNext() {
        Current=Current.Next;
    }
    public T retrieve() {
        return Current.Data;
    }
    public void update(T Val) {
        Current.Data=Val;
    }
    public void insert(T Val) {
        Num++;
        Node<T> tmp;
        if(empty()) {
            Current=Head=new Node<T>(Val);
        }
        else {
            tmp=Current.Next;
            Current.Next=new Node<T>(Val);
            Current=Current.Next;
            Current.Next=tmp;
        }
    }
    public void remove() {
        if(Current==Head) {
            Head=Head.Next;
        }else {
            Node<T> tmp=Head;
            while(tmp.Next!=Current)
                tmp=tmp.Next;
            tmp.Next=Current.Next;
        }
        if(Current.Next==null)
            Current=Head;
        else
            Current=Current.Next;
    }
    public boolean full() {
        return false;
    }
    public boolean empty() {
        return Head==null;
    }
    public boolean last() {
        return Current.Next==null;
    }
    public void display() {
        if(this==null)
            System.out.println("List is Null!");
        if(Head==null)
            System.out.println("List is Empty! ");
        Node<T> q=Head;
        while(q!=null) {
            System.out.print(q.Data+" ");
            q=q.Next;
        }
    }
    public boolean search(T x) {
        Node<T> tmp = Head;
        while (tmp != null) {
            if (tmp.Data.equals(x))
                return true;

            tmp = tmp.Next;
        }
        return false;
    }
}
