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

    private int completionTime;
    private int[] Sequence;
    private int[] processingTime;
    private int[] releaseDate;
    private int[] dueDate;
    private int[] revenue;

//  private double [] r;       //  release date.
//  private double [] p;       //  processing time
//  private double [] d;       //  due-date
//  private double [] d_bar;   //  deadline
//  private double [] e;       //  revenue
//  private double [] w;       //  weight
//  private double [][] s;     //  setup times
//  private int size;          //  instance lengh
    /**
     * @param args the command line arguments
     */
    public void setSequence(int[] Sequence) {
        this.Sequence = Sequence;
    }

    public void setProcessingTime(int[] processingTime) {
        this.processingTime = processingTime;
    }

    public void setReleaseDate(int[] releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDueDate(int[] dueDate) {
        this.dueDate = dueDate;
    }

    public void setRevenue(int[] revenue) {
        this.revenue = revenue;
    }

    public static void main(String[] args) {
        //create Sequence,processingTime,ri
        TimeSchedule ts = new TimeSchedule();
        ts.setSequence(new int[]{1, 2, 5, 4, 3});
        ts.setReleaseDate(new int[]{1, 3, 7, 5, 9}); //Goods arrived Date.
        ts.setProcessingTime(new int[]{2, 4, 8, 6, 10});
        ts.setDueDate(new int[]{4, 7, 8, 15, 20});
        ts.setRevenue(new int[]{15, 10, 6, 6, 7});

        //result
        System.out.println("Income : \t" + ts.Sorting());
    }

    private int Sorting() {
        int[] tempCompletionTime = new int[Sequence.length];
        int[] temprevenue = new int[Sequence.length];
        int income = 0;

        //Processing the completion time
        for (int i = 0; i < Sequence.length; i++) {
            int index = Sequence[i] - 1;
            if (completionTime > releaseDate[index]) {
                completionTime += processingTime[index];
            } else {
                completionTime = processingTime[index] + releaseDate[index];
            }

            if (dueDate[index] >= completionTime) {
                temprevenue[i] += revenue[index];
                income += revenue[index];
            }

            tempCompletionTime[i] = completionTime;

        }

        //print Sequence
        System.out.print("Sequence : " + "\t");
        for (int i = 0; i < Sequence.length; i++) {
            System.out.print(Sequence[i] + "\t");
        }
        System.out.println();

        //print completionTime
        System.out.print("CompletionTime : ");
        for (int i = 0; i < tempCompletionTime.length; i++) {
            System.out.print(tempCompletionTime[i] + "\t");
        }
        System.out.println();

        //print dueDate
        System.out.print("dueDate : " + "\t");
        for (int i = 0; i < dueDate.length; i++) {
            System.out.print(dueDate[i] + "\t");
        }
        System.out.println();

        //print Income
        System.out.print("Revenue : " + "\t");
        for (int i = 0; i < temprevenue.length; i++) {
            System.out.print(temprevenue[i] + "\t");
        }
        System.out.println();

        return income;
    }

}
