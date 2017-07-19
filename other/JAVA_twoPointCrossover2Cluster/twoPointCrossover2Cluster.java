/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openga.operator.crossover;
/********************************
 * <p>Title: School_Program </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */
public class twoPointCrossover2Cluster extends twoPointCrossover2 {
  /************************************************************************
   * Chromsome 1: 3 1 4 0 2 2 4 1
   * Chromsome 2: 1 4 3 0 2 4 2 1
   *
   * Then, we random generate two cut points at the position
   * Chromsome 1: 3 1 | 3 0 2 4 | 4 1
   * Chromsome 2: 1 4 | 4 0 2 2 | 2 1
   *
   * Therefore, the new chromosomes are as following:
   * Chromsome 1: 3 1 4 0 2 2 4 1
   * Chromsome 2: 1 4 3 0 2 4 2 1
   ****************************************************************************/

public void startCrossover(){
    for(int i = 0 ; i < popSize ; i ++ ){
       //test the probability is larger than crossoverRate.
       if(Math.random() <= crossoverRate){
         //to get the other chromosome to crossover
         int index2 = 0;
//         if(i % 2 == 0){
//           index2 = i+1;
//         }
//         else if(i % 2 == 1 && i > 1){
//           index2 = i-1;
//         }
//         else{
//           index2 = 0;
//         }

         setCutpoint();
         copyElements(i, index2);
         copyElements(index2, i);
       }
    }
  }  

 private void copyElements(int index1, int index2){
    //to modify the first chromosome between the index1 to index2, which genes
    //is from chromosome 2.
    int counter = 0;
    for(int i = cutPoint1 ; i <= cutPoint2; i ++ ){
//      while(checkConflict(newPop.getSingleChromosome(index2).genes[counter], newPop.getSingleChromosome(index1).genes) == true){
//        counter ++;
//      }
      newPop.setGene(index1, i, newPop.getSingleChromosome(index2).genes[counter]);
      counter ++;
    }
  }
  
  public static void main(String[] args) {
    System.out.println();
  }
  
}
