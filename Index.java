public class Index {
    LinkedList<Document> Documents;
    public Index() {
        Documents =new LinkedList<Document>();
    }
    public LinkedList<Integer> getDocumentTerm(String Term) {
        LinkedList<Integer> Check=new LinkedList<>();
        if(Documents.empty()){
            System.out.println("Document is empty");
            return null;
        }
        Documents.findFirst();
        while(!Documents.last()){
            if(Documents.retrieve().Word.search(Term.toLowerCase().trim()))
                Check.insert(Documents.retrieve().ID);
            Documents.findNext();
        }
        if(Documents.retrieve().Word.search(Term.trim().toLowerCase()))
            Check.insert(Documents.retrieve().ID);
        return Check;
    }
    public void Add_Doc(Document s) {
        Documents.insert(s);
    }
    public void DisplayDoc() {
        if(Documents ==null) {
            System.out.println("We don't have any Document");
            return;
        }
        else if(Documents.empty()) {
            System.out.println("The Document is empty");
            return;}
        Documents.findFirst();
        while(!Documents.last()) {
            Document D= Documents.retrieve();
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("ID "+ D.ID);
            D.Word.display();
            Documents.findNext();
        }
        Document D= Documents.retrieve();
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("ID "+ D.ID);
        D.Word.display();

    }
    public Document GetDocByID(int id) {
        if(Documents.empty()) {
            System.out.println("no documents exist");
            return null;
        }
        Documents.findFirst();
        while(!Documents.last()) {
            if(Documents.retrieve().ID == id)
                return Documents.retrieve();
            Documents.findNext();
        }
        if(Documents.retrieve().ID == id)
            return Documents.retrieve();
        return null;
    }
}
