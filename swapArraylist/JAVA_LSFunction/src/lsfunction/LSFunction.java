/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsfunction;

import java.util.*;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Swap the values in the arraylist</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */
public class LSFunction {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //test
        ArrayList list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ArrayList result = new ArrayList();

        LSFunction ls = new LSFunction();
        result = ls.LSF1(list, 1, 4);

        //result
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i) + "\t");
        }

    }

    private static ArrayList LSF1(ArrayList list, int x, int y) {
        int temp = (int) list.get(x);
        list.remove(x);
        list.add(x,list.get(y-1));
        list.remove(y);
        list.add(y,temp);
        return list;
    }

}
