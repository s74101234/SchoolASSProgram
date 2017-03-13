/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shiftmutation;

import java.io.*;
import java.util.*;

/**
 *
 * @author Guo Yu-Cheng Description { 從LIST中取出一個數值並重新插入到LIST裡面 }
 */
public class Shiftmutation {

    public static void main(String[] args) {

        Shiftmutation sm = new Shiftmutation();

        ArrayList list = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

//        sm.printlist(list);
        sm.getlistvalue(list, 3);
    }

    public static void printlist(ArrayList list) {
        //Starting value of list
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();
    }

    public ArrayList<Integer> getlistvalue(ArrayList list, int x) {
        
        
        
        return list;
    }

}
