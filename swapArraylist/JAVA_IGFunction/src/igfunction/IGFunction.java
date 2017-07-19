/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package igfunction;

import java.util.*;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Random get out values of the arraylist and reinsert the arraylist.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */
public class IGFunction {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //test
        ArrayList list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ArrayList result = new ArrayList();

        IGFunction ig = new IGFunction();
        result = ig.IGF1(list, 1);

        //result
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + "\t");
        }
    }

    private static ArrayList IGF1(ArrayList list, int x) {
        ArrayList savevalue = new ArrayList();

        //set random
        int takeout;
        int putin;
        Random random = new Random();

        for (int count = 0; count < x; count++) {
            //takeout random
            takeout = random.nextInt(list.size());

            //save removed value
            savevalue.add(list.get(takeout));

            //remove random list
            list.remove(takeout);
        }

        putin = random.nextInt(list.size());
        for (int i = 0; i < savevalue.size(); i++) {
            list.add(putin, savevalue.get(i));
        }

        return list;
    }
}
