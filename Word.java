public class Word {
    String Text;
    LinkedList<Integer> DOC_ID; //Document id
    public Word(String x) {
        Text=x;
        DOC_ID=new LinkedList<Integer>();
    }
    public boolean IsExists_DOC_ID(Integer id) {
        if(DOC_ID.empty())
            return false;
        while(!DOC_ID.last()) {
            if(DOC_ID.retrieve().equals(id)) {
                return true;
            }
            DOC_ID.findNext();
        }
        if(DOC_ID.retrieve().equals(id)) {
            return true;}
        return false;
    }
    public void Add_ID(int id) {
        if(!IsExists_DOC_ID(id))
            DOC_ID.insert(id);}
    public void display() {
        System.out.println("Term: " + Text);
        System.out.print(" ( ");
        DOC_ID.display();
        System.out.print(" )");
        System.out.println("\n----------------------------------");

    }
}
