package openga.operator.mutation;
import openga.chromosomes.*;
/**
 * <p>Title: The OpenGA project which is to build general framework of Genetic algorithm.</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-Hsin
 * @version 1.0
 */

public class swapMutation extends inverseMutation {
  public swapMutation() {
  }

  public void startMutation(){
    for(int i = 0 ; i < popSize ; i ++ ){
       //test the probability is larger than crossoverRate.
       if(Math.random() <= mutationRate){
         setCutpoint();
         pop.setChromosome(i, swaptGenes(pop.getSingleChromosome(i)));
       }
    }
  }

  public final chromosome swaptGenes(chromosome _chromosome){
    int backupGenes = _chromosome.genes[cutPoint1];
    _chromosome.genes[cutPoint1] = _chromosome.genes[cutPoint2];
    _chromosome.genes[cutPoint2] = backupGenes;
    return _chromosome;
  }
  
  public static void main(String[] args) {

    swapMutation swapMutation1 = new swapMutation();
    populationI population1 = new population();
    populationI newPop = new population();
    int size = 2, length = 10;
    openga.util.printClass printClass1 = new openga.util.printClass();

    population1.setGenotypeSizeAndLength(true, size, length,2);
    population1.createNewPop();

    for(int i = 0 ; i < size ; i ++ ){
       printClass1.printMatrix(""+i,population1.getSingleChromosome(i).genes);
    }

    double mutationRate = 0.95;
    swapMutation1.setData(mutationRate, population1);
    swapMutation1.startMutation();

    newPop = swapMutation1.getMutationResult();

    for(int i = 0 ; i < size ; i ++ ){
       printClass1.printMatrix(""+i,newPop.getSingleChromosome(i).genes);
    }

  }

}