import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        /*System.out.println("Please enter the size of the board: ");
        Scanner sc = new Scanner(System.in);
        int boardSize = sc.nextInt();*/
        Board board = new Board(8);

        Genetic g = new Genetic();
        int minFitness = board.getMinFitness();
        System.out.println("Min fitness: " + minFitness);
        Chromosome x = g.geneticAlgorithm(1000, 0.08, minFitness, 1000000);
        x.print();
        System.out.println(x.getFitness());
    }
}
