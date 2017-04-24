/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeschedule;

import java.text.DecimalFormat;

/**
 *
 * @author Guo Yu-Cheng
 */
public class TimeSchedule {

    private int tempcompletionTime = 0;
    private int[] completionTime;
    private int[] Sequence;
    private int[] processingTime;
    private int[] releaseDate;
    private int[] dueDate;
    private int[] deadline;
    private double[] revenue;

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
    public void setcompletionTime(int[] completionTime) {
        this.completionTime = completionTime;
    }

    public void setSequence(int[] Sequence) {
        this.Sequence = Sequence;
    }

    public void setProcessingTime(int[] processingTime) {
        this.processingTime = processingTime;
    }

    public void setReleaseDate(int[] releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setDueDate(int[] setdueDate) {
        this.dueDate = setdueDate;
    }

    public void setDeadline(int[] deadline) {
        this.deadline = deadline;
    }

    public void setRevenue(double[] revenue) {
        this.revenue = revenue;
    }

    public static void main(String[] args) {
        //create Sequence,processingTime,ri
        TimeSchedule ts = new TimeSchedule();
        ts.setcompletionTime(new int[5]);
        ts.setSequence(new int[]{2, 1, 5, 3, 4});
        ts.setReleaseDate(new int[]{1, 3, 7, 5, 9}); //Goods arrived Date.
        ts.setProcessingTime(new int[]{2, 5, 8, 6, 10});
        ts.setDueDate(new int[]{4, 7, 8, 14, 20});
        ts.setDeadline(new int[]{6, 9, 10, 17, 22});
        ts.setRevenue(new double[]{15, 10, 6, 6, 7});

        //result
        System.out.println("TotalRevenue : \t" + ts.Sorting());
    }

    private double Sorting() {
        //The second decimal point
        DecimalFormat df = new DecimalFormat("##.00");

        double totalRevenue = 0;
        double[] temprevenue = new double[Sequence.length];

        for (int index = 0; index < Sequence.length; index++) {
            if (tempcompletionTime <= releaseDate[index]) {
                tempcompletionTime = releaseDate[index] + processingTime[index];
                completionTime[index] = tempcompletionTime;
            } else {
                tempcompletionTime += processingTime[index];
                completionTime[index] = tempcompletionTime;
            }
                        
            if (completionTime[index] <= dueDate[index]) {
                temprevenue[index] = revenue[index];
                totalRevenue += temprevenue[index];
            } else if (completionTime[index] > dueDate[index] && completionTime[index] < deadline[index]) {
                temprevenue[index] = (revenue[index] * completionTime[index] / (dueDate[index] + deadline[index]));

                //The second decimal point
                temprevenue[index] = Double.parseDouble(df.format(temprevenue[index]));
                totalRevenue += temprevenue[index];
            } else {
                temprevenue[index] = 0;
                tempcompletionTime = completionTime[index - 1];
            }
        }

        //print Sequence
        System.out.print("Sequence : " + "\t");
        for (int i = 0; i < Sequence.length; i++) {
            System.out.print(Sequence[i] + "\t");
        }
        System.out.println();

        //print ReleaseDate
        System.out.print("ReleaseDate : " + "\t");
        for (int i = 0; i < releaseDate.length; i++) {
            System.out.print(releaseDate[i] + "\t");
        }
        System.out.println();

        //print processingTime
        System.out.print("ProcessingTime : ");
        for (int i = 0; i < processingTime.length; i++) {
            System.out.print(processingTime[i] + "\t");
        }
        System.out.println();

        //print completionTime
        System.out.print("CompletionTime : ");
        for (int i = 0; i < completionTime.length; i++) {
            System.out.print(completionTime[i] + "\t");
        }
        System.out.println();

        //print dueDate
        System.out.print("DueDate : " + "\t");
        for (int i = 0; i < dueDate.length; i++) {
            System.out.print(dueDate[i] + "\t");
        }
        System.out.println();

        //print deadline
        System.out.print("Deadline : " + "\t");
        for (int i = 0; i < deadline.length; i++) {
            System.out.print(deadline[i] + "\t");
        }
        System.out.println();

        //print Income
        System.out.print("Revenue : " + "\t");
        for (int i = 0; i < temprevenue.length; i++) {
            System.out.print(temprevenue[i] + "\t");
        }
        System.out.println();

        return totalRevenue;
    }

}
