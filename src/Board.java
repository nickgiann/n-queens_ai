public class Board {

    private static int boardSize;
    int minFitness;

     Board(int n) {
        boardSize = n;
        minFitness = this.calculateMinFitness(n);
    }

    public static int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getMinFitness() {
        return minFitness;
    }

    public void setMinFitness(int minFitness) {
        this.minFitness = minFitness;
    }

    private int calculateMinFitness(int n) {
         int minFitness = 0;
         for (int i = n; i>0; i--){
             minFitness += n - i;
         }
         return minFitness;
    }

}

