/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeschedule;

import java.util.*;

/**
 *
 * @author Guo Yu-Cheng
 */
public class TimeSchedule {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //create list,pi,ri
        ArrayList<Integer> list = new ArrayList(Arrays.asList(5,3,1,2,4));
        ArrayList<Integer> pi = new ArrayList(Arrays.asList(2,4,8,6,10));
        ArrayList<Integer> ri = new ArrayList(Arrays.asList(1,3,7,5,9));

        int result = 0;
        TimeSchedule ts = new TimeSchedule();

        //result
        result = ts.Sequence(list, pi, ri);
        System.out.println(result);

    }

    private static int Sequence(ArrayList<Integer> list, ArrayList<Integer> pi, ArrayList<Integer> ri) {
        int sum = 0;

        //print startlist
        System.out.print("Sequence : " + "\t");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }
        System.out.println();

        //Processing the completion time
        System.out.print("Completion: " + "\t");

        for (int i = 0; i < list.size(); i++) {
            if (sum > ri.get(list.get(i) - 1)) {
                sum += pi.get(list.get(i) - 1);
            } else {
                sum += pi.get(list.get(i) - 1) + (ri.get(list.get(i) - 1) - sum);
            }
            
            System.out.print(sum + "\t");
        }

        return sum;
    }

}
