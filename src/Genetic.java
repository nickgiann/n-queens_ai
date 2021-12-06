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
        int intersectionPoint = r.nextInt(boardSize -1 ) + 1;
        //System.out.println("Intersection point: " + intersectionPoint);
        int [][] childGenes = new int[2][boardSize];

        for(int i=0; i<intersectionPoint; i++)
        {
            childGenes[0][i] = y.getGenes()[i];
            childGenes[1][i] = x.getGenes()[i];
        }
//        System.out.println("Now in after intersection point:");
//        System.out.println(childGenes.length);
        for(int i=intersectionPoint; i<boardSize; i++)
        {
//            System.out.println("x" + i + ": "+ x.getGenes()[i]);
//            System.out.println("y" + i + ": "+ y.getGenes()[i]);
            childGenes[0][i] = x.getGenes()[i];
            childGenes[1][i] = y.getGenes()[i];
        }
        return childGenes;
    }

/*    public Chromosome reproduce(Chromosome x, Chromosome y)
    {
        Random r = new Random();
        //Randomly choose the intersection point
        int intersectionPoint = r.nextInt(7) + 1;
        int [] childGenes = new int[boardSize];
        //The child has the left side of the x chromosome up to the intersection point...
        for(int i=0; i<intersectionPoint; i++)
        {
            childGenes[i] = x.getGenes()[i];
        }
        //...and the right side of the y chromosome after the intersection point
        for(int i=intersectionPoint; i<childGenes.length; i++)
        {
            childGenes[i] = y.getGenes()[i];
        }
        //Dimiourgoume mono ena paidi. Idaniko einai na epitrefoume kai ta dyo
        return new Chromosome(childGenes);
    }*/

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

//                System.out.print("\nParent 1: ");
//                x.print();
//                System.out.print("Parent 2: ");
//                y.print();
//                System.out.print("Child A: ");
//                childA.print();
//                System.out.print("Child B: ");
//                childB.print();
//                System.out.println("\n");

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

/*    public Chromosome geneticAlgorithm(int populationSize, double mutationProbability, int minimumFitness, int maximumSteps)
    {
        //We initialize the population
        initializePopulation(populationSize); //The Arraylist containing the chromosomes and the fitnessbounds arraylist are created
        Random r = new Random();
        for(int step=0; step < maximumSteps; step++)
        {
            //Initialize the new generated population
            ArrayList<Chromosome> newPopulation = new ArrayList<Chromosome>();
            for(int i=0; i < populationSize; i++)
            {
                //We choose two chromosomes from the population
                //Due to how fitnessBounds ArrayList is generated, the propability of
                //selecting a specific chromosome depends on its fitness score
                int xIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                Chromosome x = this.population.get(xIndex);
                int yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                while(yIndex == xIndex)
                {
                    yIndex = this.fitnessBounds.get(r.nextInt(this.fitnessBounds.size()));
                }
                Chromosome y = this.population.get(yIndex);
                //We generate the "child" of the two chromosomes
                Chromosome child = this.reproduce(x, y);
                //We might then mutate the child
                if(r.nextDouble() < mutationProbability)
                {
                    child.mutate();
                }
                //...and finally add it to the new population
                newPopulation.add(child);
            }
            this.population = new ArrayList<Chromosome>(newPopulation);

            //We sort the population so the one with the greater fitness is first
            Collections.sort(this.population, Collections.reverseOrder());
            //If the chromosome with the best fitness is acceptable we return it
            if(this.population.get(0).getFitness() >= minimumFitness)
            {
                System.out.println("Finished after " + step + " steps...");
                return this.population.get(0);
            }
            //We update the fitnessBounds arrayList
            this.updateFitnessBounds();
        }

        System.out.println("Finished after " + maximumSteps + " steps...");
        return this.population.get(0);
    }*/
}
