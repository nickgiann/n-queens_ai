import java.util.Random;

public class Chromosome implements Comparable<Chromosome>{

    //We assume that every column has exactly one Queen
    //Each position represents the position of the queen in the corresponding column
    private int [] genes;
    private int fitness; //Holds the fitness score of the chromosome
    private int boardSize = Board.getBoardSize();

    public Chromosome() {
        genes = new int[boardSize];
        Random r = new Random();
        for (int i = 0; i<boardSize; i++) {
            genes[i] = r.nextInt(boardSize);
        }
        this.calculateFitness();
    }

    public Chromosome(int [] genes) {
        //System.out.println("Boardsize in chromosome: " + boardSize);
        this.genes = new int[boardSize];
        //System.out.println("Genes size: " + this.genes.length);
        for (int i = 0; i<boardSize; i++) {
            //System.out.println(i);
            this.genes[i] = genes[i];
        }
        this.calculateFitness();
    }

    public void calculateFitness(){
        int non_threats = 0;
        for (int i = 0; i<genes.length; i++) {
            for (int j = i+1; j<genes.length; j++) {
                if((this.genes[i] != this.genes[j]) && (Math.abs(i - j) != Math.abs(this.genes[i] - this.genes[j]))) {
                    non_threats++;
                }
            }
        }
        fitness = non_threats;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void mutate() {
        Random r = new Random();
        genes[r.nextInt(boardSize)] = r.nextInt(boardSize);
        this.calculateFitness();
    }

    public void print(){
        System.out.print("|");
        for(int i=0; i<genes.length; i++)
        {
            System.out.print(this.genes[i]);
            System.out.print("|");
        }
        System.out.print(" : ");
        System.out.println(this.fitness);

        System.out.println("------------------------------------");
        for(int i=0; i< genes.length; i++)
        {
            for(int j=0; j < genes.length; j++)
            {
                if(genes[j] == i)
                {
                    System.out.print("|Q");
                }
                else
                {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
        }
        System.out.println("------------------------------------");
    }

    @Override
    public boolean equals(Object obj){
        for(int i=0; i<this.genes.length; i++)
        {
            if(this.genes[i] != ((Chromosome)obj).genes[i])
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hashcode = 0;
        for(int i=0; i<this.genes.length; i++)
        {
            hashcode += this.genes[i];
        }
        return hashcode;
    }

    @Override
    public int compareTo(Chromosome x){
        return this.fitness - x.fitness;
    }
}
