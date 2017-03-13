/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igstep1;
import java.util.*;

/**
 *
 * @author Guo Yu-Cheng
 * Description 
 * {
 *  The list randomly takes three values 
 *  and then reinserts the list.
 * }
 */
public class IGStep1 {

    public static void main(String[] args) {
        //test
        ArrayList list = new ArrayList(Arrays.asList(1, 2, 3,4,5,6,7,8,9,10));
        ArrayList result = new ArrayList();
        
//        //Starting value of list
//        for(int i = 0 ; i < list.size();i++)
//        {
//            System.out.print(list.get(i) + "\t");
//        }
//        System.out.println();
        
        IGStep1 igs = new IGStep1();
        result = igs.Step1(list,1);
        
        //result
        for(int i = 0 ; i < result.size();i++)
        {
            System.out.print(result.get(i) + "\t");
        }
    }
    
    private static ArrayList Step1(ArrayList list,int x)
    {
        ArrayList savevalue = new ArrayList();
        
        //set random
        int takeout ;
        int putin ;
        Random random = new Random();
        
            for(int count = 0 ; count < x ; count++ ){
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
        
        return list;
    }
}
