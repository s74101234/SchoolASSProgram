/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsfunction;

import java.util.*;

/**
 *
 * @author Guo Yu-Cheng
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
        result = ls.LSF1(list, 0, 2);

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
