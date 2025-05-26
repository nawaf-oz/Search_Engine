public class Ranking {
    static String Query;
    static InvertedindexBST InvertedBST;
    static Index Index;
    static LinkedList<Integer> AllDocInQuery;
    static LinkedList<DocRank> AllDocRank;
    public Ranking (InvertedindexBST inverted , Index index ,String query){
        InvertedBST = inverted;
        Index =index;
        Query=query;
        AllDocInQuery = new LinkedList<Integer>();
        AllDocRank = new LinkedList<DocRank> () ;
    }
    public static void displayRanking() {
        if (AllDocRank.empty()) {
            System.out.println("empty rank");
            return;
        }
        System.out.printf("%-8s%-8s\n", "DocID", "Score");
        AllDocRank.findFirst();
        while (!AllDocRank.last()) {
            AllDocRank.retrieve().display();
            AllDocRank.findNext();
        }
        AllDocRank.retrieve().display();
    }
    public static Document GetDocByID(int id){ // this method retrieve the Document by given id
        return Index.GetDocByID(id);
    }
    public static int getScore(Document d, String Query) {
        LinkedList<String> terms = d.Word;
        if (Query.length() == 0 || terms.empty())
            return 0;
        int score = 0;
        String[] T = Query.split(" ");
        int i = 0;
        while (i < T.length) {
            String term = T[i].trim().toLowerCase();
            int repeat = 0;
            terms.findFirst();
            while (!terms.last()) {
                if (terms.retrieve().equalsIgnoreCase(term))
                    repeat++;
                terms.findNext();
            }
            if (terms.retrieve().equalsIgnoreCase(term))
                repeat++;
            score += repeat;
            i++;
        }
        return score;
    }


    public static boolean IsExistsInResult(LinkedList<Integer>result, Integer id){
        if(result.empty()) return false;
        result.findFirst();
        while (!result.last()){
            if (result.retrieve().equals(id)){
                return true;
            }
            result.findNext();
        }

        if (result.retrieve().equals(id)){
            return true;
        }
        return false;
    }
    public static void AddingInIdsList(LinkedList<Integer> LL){
        if(LL.empty())
            return;
        LL.findFirst();
        while(!LL.empty()){
            boolean Found = IsExistsInResult(AllDocInQuery, LL.retrieve());
            if(!Found){
                AllDocInQuery.insert(LL.retrieve());
            }
            if(!LL.last())
                LL.findNext();
            else
                break;
        }
    }
    public static void RankRetrival(String Query){
        LinkedList<Integer> LL = new LinkedList<Integer>();
        if(Query.length()==0)
            return ;
        String[] T = Query.split(" ");
        boolean found = false;
        int i = 0;
        while (i < T.length) {
            found= InvertedBST.SearchWordInInverted(T[i].trim().toLowerCase());
            if(found){
                LL = InvertedBST.Inverted_Index.Retrieve().DOC_ID;
                AddingInIdsList(LL);
            }
            i++;
        }
    }

    private static void insertSortedRank(DocRank douR) {
        if (AllDocRank.empty()) {
            AllDocRank.insert(douR);
            return;
        }
        AllDocRank.findFirst();
        while (!AllDocRank.last()) {
            if (douR.Rank > AllDocRank.retrieve().Rank) {
                DocRank dr1 = AllDocRank.retrieve();
                AllDocRank.update(douR);
                AllDocRank.insert(dr1);
                return;
            } else
                AllDocRank.findNext();
        }
        if (douR.Rank >AllDocRank.retrieve().Rank) {
            DocRank dr1 = AllDocRank.retrieve();
            AllDocRank.update(douR);
            AllDocRank.insert(dr1);
            return;
        } else
            AllDocRank.insert(douR);

    }
    public static void insertSortedByRank() {
        RankRetrival(Query);
        if (AllDocInQuery.empty()) {
            System.out.println("the query doesn't exist");
            return;
        }
        AllDocInQuery.findFirst();
        while (!AllDocInQuery.last()) {
            Document Doc = GetDocByID(AllDocInQuery.retrieve());
            int Rank = getScore(Doc, Query);
            DocRank DocR = new DocRank(AllDocInQuery.retrieve(), Rank);
            insertSortedRank(DocR);
            AllDocInQuery.findNext();
        }
        Document Doc = GetDocByID(AllDocInQuery.retrieve());
        int Rank = getScore(Doc, Query);
        DocRank DocR = new DocRank(AllDocInQuery.retrieve(), Rank);
        insertSortedRank(DocR);
    }

}
