import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    static LinkedList <String> StopWord;
    static Index Index;
    static InvertedIndex Inverted;
    static InvertedindexBST InvertedBST;
    int Tokens=0;
    LinkedList <String> UniqueWords=new LinkedList<String>();
    static Scanner input=new Scanner(System.in);
    public Main(){
        StopWord = new LinkedList<>();
        Index= new Index();
        Inverted =new InvertedIndex();
        InvertedBST =new InvertedindexBST();
    }
    public void LoadStopWords (String fileName){
        try {
            File f=new File (fileName);
            Scanner s=new Scanner (f);
            while (s.hasNextLine()) {
                String line=s.nextLine();
                StopWord.insert(line);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void LoadAllDoc (String fileName) {
        String line = null;
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            s.nextLine();
            while (s.hasNextLine()) {
                line = s.nextLine();
                if (line.trim().length() < 3) {
                    break;
                }
                String x = line.substring(0, line.indexOf(","));
                int ID = Integer.parseInt(x.trim());
                String Content = line.substring(line.indexOf(",") + 1);
                LinkedList<String> WordInDoc = LLOfWordInIndexOfInvertedIndex(Content, ID);
                Index.Add_Doc(new Document(ID, WordInDoc,Content));
                CountTokenANDUniqueWords(Content);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    public LinkedList<String> LLOfWordInIndexOfInvertedIndex (String Content, int ID){
        LinkedList<String> WordsInDoc = new LinkedList<String>() ;
        IndexOfInvertedIndex(Content, WordsInDoc, ID) ;
        return WordsInDoc;
    }
    public void IndexOfInvertedIndex (String Content, LinkedList<String>WordsInDoc, int ID) {
        while (Content.contains("-")){
            if (Content.charAt(Content.indexOf("-")-2)==' ')
                Content=Content.replaceFirst("-","");
            else
                Content=Content.replaceFirst("-"," ");
        }
        Content = Content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        String[] tokens = Content.split("\\s+");
        for(String w : tokens){
            if (!IsExistInStopWord(w)) {
                WordsInDoc.insert(w);
                Inverted.add(w, ID);
                InvertedBST.add(w, ID);
            }
        }
    }
    public void CountTokenANDUniqueWords(String Content) {
        Content=Content.replaceAll("\'"," ");
        Content=Content.replaceAll("-"," ");
        Content = Content.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "");
        String[] tokens = Content.split("\\s+");
        Tokens+=tokens.length;
        for (String w : tokens) {
            if (!UniqueWords.search(w)){
                UniqueWords.insert(w);
            }
        }
    }
    public boolean IsExistInStopWord (String Word) {
        if (StopWord==null || StopWord.empty())
            return false;
        StopWord.findFirst();
        while (!StopWord.last()) {
            if (StopWord.retrieve().equals(Word)) {
                return true;
            }
            StopWord.findNext();
        }
        if(StopWord.retrieve().equals(Word))
            return true;

        return false;
    }
    public void LoadFiles (String StopFile,String DocumentFile) {
        LoadStopWords(StopFile);
        LoadAllDoc(DocumentFile);
    }
    public void DisplayDocWithID(LinkedList<Integer> Id){
        if(Id.empty()){
            System.out.println("No Document Found");
            return;
        }
        Id.findFirst();
        while (!Id.last()) {
            Document D=Index.GetDocByID(Id.retrieve());
            if(D!=null){
                System.out.println("Document "+D.ID );
            }
            Id.findNext();
        }
        Document D=Index.GetDocByID(Id.retrieve());
        if(D!=null){
            System.out.println("Document "+D.ID );
        }
        System.out.println("");
    }
    public static void showMenuOptions() {
        System.out.println("Select an option between 1 and 11:");
        System.out.println("1 - Retrieve Specific Term");
        System.out.println("2 - Display All Documents");
        System.out.println("3 - Display Inverted Index (List Version)");
        System.out.println("4 - Display Inverted Index (BST Version)");
        System.out.println("5 - Perform Boolean Search");
        System.out.println("6 - Ranked Search");
        System.out.println("7 - Show Total Documents in Index");
        System.out.println("8 - Show Count of Unique Terms in Index");
        System.out.println("9 - Show Unique Word Count");
        System.out.println("10 - Show Tokens Count");
        System.out.println("11 - Exit Program");
    }

    public static void mainMenu() {
        Main mainInstance = new Main();
        mainInstance.LoadFiles("stop.txt", "dataset.csv");
        int userChoice = 0;
        do {
            showMenuOptions();
            userChoice = input.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.println("Enter term to retrieve:");
                    String term = input.next().toLowerCase().trim();
                    System.out.println("Using List-based Index");
                    LinkedList<Integer> result = Main.Index.getDocumentTerm(term);
                    System.out.print("Term: " + term + "\n( ");
                    result.display();
                    System.out.println(" )");
                    System.out.println("----------------------------------");
                    System.out.println("Using Inverted Index (List)");
                    if (mainInstance.Inverted.search_inverted_word_(term)) {
                        mainInstance.Inverted.WordList.retrieve().display();
                    } else {
                        System.out.println("Term Not Found");
                    }
                    System.out.println("Using Inverted Index (BST)");
                    if (mainInstance.InvertedBST.SearchWordInInverted(term)) {
                        mainInstance.InvertedBST.Inverted_Index.Retrieve().display();
                    } else {
                        System.out.println("Term Not Found");
                    }
                    break;
                case 2:
                    mainInstance.Index.DisplayDoc();
                    System.out.println("\n");
                    break;
                case 3:
                    mainInstance.Inverted.display_inverted_index();
                    break;
                case 4:
                    mainInstance.InvertedBST.DisplayInvertedIndex();
                    break;
                case 5:
                    input.nextLine();
                    System.out.println("Enter query for retrieval:");
                    String query = input.nextLine().toLowerCase();
                    query = query.replaceAll(" and ", " AND ").replaceAll(" or ", " OR ");
                    System.out.println("\nChoose retrieval method:\n" +
                            "1 - Index-based Retrieval\n" +
                            "2 - Inverted Index Retrieval\n" +
                            "3 - BST-based Retrieval\n" +
                            "4 - Exit Retrieval");
                    int retrievalOption = input.nextInt();
                    while (retrievalOption != 4) {
                        switch (retrievalOption) {
                            case 1:
                                QueryIndex indexSearch = new QueryIndex(Main.Index);
                                System.out.println("--- " + query + " ---");
                                LinkedList resultLL = QueryIndex.MixedQuery(query);
                                mainInstance.DisplayDocWithID(resultLL);
                                break;
                            case 2:
                                QueryP invertedSearch = new QueryP(mainInstance.Inverted);
                                System.out.println("--- " + query + " ---");
                                LinkedList resultInverted = QueryP.MixedQuery(query);
                                mainInstance.DisplayDocWithID(resultInverted);
                                break;
                            case 3:
                                QueryPBST bstSearch = new QueryPBST(mainInstance.InvertedBST);
                                System.out.println("--- " + query + " ---");
                                LinkedList resultBST = QueryPBST.MixedQuery(query);
                                mainInstance.DisplayDocWithID(resultBST);
                                break;
                            default:
                                System.out.println("Invalid choice, please try again.");
                                break;
                        }
                        System.out.println("\nChoose retrieval method:\n" +
                                "1 - Index-based Retrieval\n" +
                                "2 - Inverted Index Retrieval\n" +
                                "3 - BST-based Retrieval\n" +
                                "4 - Exit Retrieval");
                        retrievalOption = input.nextInt();
                    }
                    break;
                case 6:
                    input.nextLine();
                    System.out.println("Enter query for ranking:");
                    String rankQuery = input.nextLine().toLowerCase();
                    Ranking rank = new Ranking(mainInstance.InvertedBST, Index, rankQuery);
                    rank.insertSortedByRank();
                    rank.displayRanking();
                    System.out.println("----------------------");
                    break;
                case 7:
                    System.out.println("Total number of documents: " + Main.Index.Documents.Num);
                    System.out.println("----------------------");
                    break;
                case 8:
                    System.out.println("Total number of unique words: " + mainInstance.Inverted.WordList.Num);
                    System.out.println("----------------------");
                    break;
                case 9:
                    System.out.println("Total unique words: " + mainInstance.UniqueWords.Num);
                    System.out.println("----------------------");
                    break;
                case 10:
                    System.out.println("Total tokens: " + mainInstance.Tokens);
                    System.out.println("----------------------");
                    break;
                case 11:
                    System.out.println("Thanks for using the search engine. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input, please enter a number between 1 and 10.");
                    break;
            }
        } while (userChoice != 11);
    }
    public static void main (String [] args) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("|            Hello and Welcome to the Search Engine            |");
        System.out.println("----------------------------------------------------------------");
        mainMenu();

    }}
