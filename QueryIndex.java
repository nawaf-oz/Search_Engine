public class QueryIndex {
static Index Index;
public QueryIndex(Index index) {
    Index = index;
}
public static LinkedList<Integer> AndQuery(String Query) {
    LinkedList<Integer> Result = new LinkedList<>();
    if (Query.isEmpty())
        return Result;
    String[] terms = Query.split("AND");
    if (terms.length == 0)
        return Result;
    Result = Index.getDocumentTerm(terms[0].trim().toLowerCase());
    for (int i = 1; i < terms.length; i++) {
        LinkedList<Integer> Result1 = Index.getDocumentTerm(terms[i].trim().toLowerCase());
        Result = AndQuery(Result, Result1);
        if (Result.empty()) {
            break;
        }
    }
    return Result;
}
    private static LinkedList<Integer> AndQuery(LinkedList<Integer> A, LinkedList<Integer> B){
        LinkedList<Integer> Result = new LinkedList<>();
        if (A.empty() || B.empty())
            return Result;
        A.findFirst();
        while (true) {
            boolean Found = ExistInRes(Result, A.retrieve());
            if (!Found) {
                B.findFirst();
                while (true) {
                    if (B.retrieve().equals(A.retrieve())) {
                        Result.insert(A.retrieve());
                        break;
                    }
                    if (!B.last())
                        B.findNext();
                    else
                        break;
                }
            }
            if (!A.last())
                A.findNext();
            else
                break;
        }
        return Result;
    }
    public static LinkedList<Integer> ORQuery(String Query) {
        LinkedList<Integer> A = new LinkedList<Integer>();
        LinkedList<Integer> B = new LinkedList<Integer>();
        String [] T = Query.split("OR");
        if (T.length == 0) return A;
        A=Index.getDocumentTerm(T[0].trim().toLowerCase());
        for (int i = 0; i < T.length; i++) {
            B= Index.getDocumentTerm(T[i].trim().toLowerCase());
            A=ORQuery(A, B); //check
        }
        return A;
    }
    private static LinkedList<Integer> ORQuery(LinkedList<Integer> A, LinkedList<Integer> B) {
        LinkedList<Integer> Result = new LinkedList<Integer>();
        if (A.empty() && B.empty())
            return Result;
        A.findFirst();
        while (!A.empty()) {
            boolean Found = ExistInRes(Result, A.retrieve());
            if (!Found) {
                Result.insert(A.retrieve());
            }
            if (!A.last())
                A.findNext();
            else
                break;
        }
        B.findFirst();
        while (!B.empty()) {
            boolean Found = ExistInRes(Result, B.retrieve());
            if (!Found) {
                Result.insert(B.retrieve());
            }
            if (!B.last())
                B.findNext();
            else
                break;
        }
        return Result;
    }
    public static LinkedList<Integer>MixedQuery(String Query) {
        LinkedList<Integer>A=new LinkedList<Integer>();
        LinkedList<Integer>B=new LinkedList<Integer>();
        if(Query.length()==0)
            return A;
        String []M= Query.split("OR");
        A=AndQuery(M[0]);
        for (int i = 1; i < M.length; i++) {
            B=AndQuery(M[i]);
            A=ORQuery(A, B);
        }
        return A;
    }
    public static boolean ExistInRes(LinkedList<Integer> Result , Integer ID) {
        if(Result.empty())
            return false;
        Result.findFirst();
        while (!Result.last()){
            if(Result.retrieve().equals(ID)){
                return true;
            }
            Result.findNext();
        }
        if(Result.retrieve().equals(ID)){
            return true;}

        return false;
    }


}
