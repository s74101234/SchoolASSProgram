/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openga.operator.mutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import openga.chromosomes.chromosome;
import openga.chromosomes.population;
import openga.chromosomes.populationI;

/**
 *
 * @author Guo Yu-Cheng
 */
public class LocalSerchByIG{
  
  public populationI pop;                 //mutation on whole population
  public double mutationRate;            //mutation rate
  public int popSize, chromosomeLength;  //size of population and number of digits of chromosome
  public int cutPoint1, cutPoint2;       //the genes between the two points are inversed
  
  public void setData(double mutationRate, populationI population1){
    pop = new population();
    this.pop = population1;
    this.mutationRate = mutationRate;
    popSize = population1.getPopulationSize();
    chromosomeLength = population1.getSingleChromosome(0).genes.length;
  }
  
  public void setData(populationI population1){
    pop = new population();
    this.pop = population1;
    popSize = population1.getPopulationSize();
    chromosomeLength = population1.getSingleChromosome(0).genes.length;
  }
  
  public void startMutation(){
    for(int i = 0 ; i < popSize ; i ++ ){
       //test the probability is larger than crossoverRate.
       if(Math.random() <= mutationRate){
         pop.setChromosome(i,iterateGenes(pop.getSingleChromosome(i),1));
       }
    }
  }
  
  public populationI getMutationResult(){
    return pop;
  }
  
  public chromosome iterateGenes(chromosome _chromosome,int number)
  {
    //set random
    int takeout,putin ;
    Random random = new Random();

    ArrayList savevalue = new ArrayList();
    ArrayList list = new ArrayList();
    for(int i = 0 ; i < _chromosome.genes.length ; i++)
    {
      list.add(_chromosome.genes[i]);
    }

    for(int count = 0 ; count < number ; count++ ){
    //takeout random
        takeout = random.nextInt(list.size());
    //save removed value
        savevalue.add(list.get(takeout));
    //remove random list
        list.remove(takeout);
        }
    putin = random.nextInt(list.size());
    for(int i=0 ; i < savevalue.size() ; i++)
    {
        list.add(putin,savevalue.get(i));
    }
    
    for(int i = 0 ; i < _chromosome.genes.length ; i++)
    {
      _chromosome.genes[i] = (int) list.get(i);
    }
    
    
    return _chromosome;
  }
  
  public static void main(String[] args) {

    LocalSerchByIG LocalSerchByIG1 = new LocalSerchByIG();
    populationI population1 = new population();
    populationI newPop = new population();
    int size = 2, length = 10;
    openga.util.printClass printClass1 = new openga.util.printClass();

    population1.setGenotypeSizeAndLength(true, size, length,2);
    population1.createNewPop();
    
    System.out.println("getSingleChromosome");
    for(int i = 0 ; i < size ; i ++ ){
       printClass1.printMatrix(""+i,population1.getSingleChromosome(i).genes);
    }

    System.out.println("Start Matation");
    double mutationRate = 0.95;
    LocalSerchByIG1.setData(mutationRate, population1);
    LocalSerchByIG1.startMutation();

    newPop = LocalSerchByIG1.getMutationResult();

    for(int i = 0 ; i < size ; i ++ ){
       printClass1.printMatrix(""+i,newPop.getSingleChromosome(i).genes);
    }

  }
}
