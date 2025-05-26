public class DocRank {
    int ID;
    int Rank;
    public DocRank(int id, int rank) {
        ID = id;
        Rank = rank;
    }
    public  void display() {
        System.out.printf("%-8d%-8d\n", ID, Rank);
    }
}
