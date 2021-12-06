import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetic {

    private ArrayList<Chromosome> population;
    private ArrayList<Integer> fitnessBounds;
    private static int boardSize = Board.getBoardSize();

    public Genetic(){
        population = null;
        fitnessBounds = null;
    }

    //Create as many chromosomes as the population size and add them to the population Arraylist
    public void initializePopulation(int populationSize){
        population = new ArrayList<Chromosome>();
        for (int i = 0; i<populationSize; i++){
            population.add(new Chromosome());
        }
        this.updateFitnessBounds();
    }

    //Add the index of each chromosome in an Arraylist as many times as its fitness score
    public void updateFitnessBounds(){
        fitnessBounds = new ArrayList<Integer>();
        for (int i = 0; i < population.size(); i++){
            for (int j = 0; j < population.get(i).getFitness(); j++)
                fitnessBounds.add(i);
        }
    }

    public int [][] reproduce(Chromosome x, Chromosome y){
        Random r = new Random();
        int [][] childGenes = new int[2][boardSize];

        double probability;
        for (int i = 0; i < boardSize; i++){
             probability = Math.random();
            if (probability < 0.5) {
                childGenes[0][i] = x.getGenes()[i];
                childGenes[1][i] = y.getGenes()[i];
            } else {
                childGenes[0][i] = y.getGenes()[i];
                childGenes[1][i] = x.getGenes()[i];
            }
        }
        return childGenes;
    }

    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int minFitness, int maxSteps){

        initializePopulation(populationSize);

        Random r = new Random();
        for(int step=0; step < maxSteps; step++)
        {
            ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
            for(int i=0; i < populationSize/2; i++)
            {
                //Select two random Chromosomes
                int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                Chromosome x = this.population.get(xIndex);
                int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                while(yIndex == xIndex)
                {
                    yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                }
                Chromosome y = this.population.get(yIndex);
                //Generate the children of the two chromosomes
                int [][] children = this.reproduce(x, y);
                Chromosome childA = new Chromosome(children[0]);
                Chromosome childB = new Chromosome(children[1]);

                //One or both children might mutate
                if(r.nextDouble() < mutationProbability)
                {
                    childA.mutate();

                }
                if(r.nextDouble() < mutationProbability)
                {
                    childB.mutate();

                }
                //Add the children to the population
                newPopulation.add(childA);
                newPopulation.add(childB);
            }
            this.population = new ArrayList<Chromosome>(newPopulation);

            //Sort the population so the one with the greater fitness is first
            Collections.sort(this.population, Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minFitness)
            {
                System.out.println("Finished after " + step + " steps...");
                return this.population.get(0);
            }
            //Update the fitnessBounds arrayList
            this.updateFitnessBounds();
        }

        System.out.println("Finished after " + maxSteps + " steps...");
        System.out.println("Population size: " + population.size());
        return this.population.get(0);
    }
}
