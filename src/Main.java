import java.util.Scanner;

public class Main {
    public static void main(String[] args){


        Scanner sc = new Scanner(System.in);
        System.out.println("Please the number of Queens: ");
        int boardsize = sc.nextInt();
        Board board = new Board(boardsize);

        Genetic g = new Genetic();
        int minFitness = board.getMinFitness();
        System.out.println("Min fitness: " + minFitness);
        long start = System.currentTimeMillis();
        Chromosome x = g.geneticAlgorithm(1000, 0.12, minFitness, 1000000000);
        long end = System.currentTimeMillis();
        x.print();
        System.out.println(x.getFitness());
        System.out.println("Search time: " + (double)(end - start) / 1000 + " sec.");
    }
}
