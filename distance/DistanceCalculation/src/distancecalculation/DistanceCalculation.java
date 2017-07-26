/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distancecalculation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Get the values of the file and use the Euclidean formula to calculate the distance sum.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */
public class DistanceCalculation {

    /**
     * @param args the command line arguments
     */
    private String readTxtPath = "@../../File/distanceData.txt";
    private String[] STxt;
    private int pointSum;
    private Double[] startingPoint;
    private Double[] DistanceDataX;
    private int xCount = 5;
    private Double[] DistanceDataY;
    private int yCount = 6;
    private Double[] startingDistanceData;
    private Double[][] DistanceData;
    private double totalDistance = 0;
    private int[] Sequence;

    private void readTxt() throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(readTxtPath);
        BufferedReader br = new BufferedReader(fr);
        String TxtAll = "", eachLine = "";
        while ((eachLine = br.readLine()) != null) {
            TxtAll += eachLine + "\n";
        }
        STxt = TxtAll.split(" |\n");
        pointSum = (Integer.parseInt(STxt[0]) - 1);
        DistanceDataX = new Double[pointSum];
        DistanceDataY = new Double[pointSum];
        DistanceData = new Double[pointSum][pointSum];

        startingDistanceData = new Double[pointSum];
        Sequence = new int[Integer.parseInt(STxt[0])];
//        for(int i = 0 ;  i < STxt.length ; i ++) 
//        {
//            System.out.println(STxt[i]);
//        }
        startingPoint = new Double[]{Double.parseDouble(STxt[2]), Double.parseDouble(STxt[3])};
        for (int i = 0; i < pointSum; i++) {
            DistanceDataX[i] = Double.parseDouble(STxt[xCount]);
            xCount += 3;
            DistanceDataY[i] = Double.parseDouble(STxt[yCount]);
            yCount += 3;
//            System.out.print(i + " : " + DistanceDataX[i] + "," + DistanceDataY[i] + "\n");
        }
//        System.out.println("startingPoint : " + startingPoint[0] + "," + startingPoint[1]);
    }

    private Double euclideanDistance(Double x1, Double y1, Double x2, Double y2) {
        Double result;
        result = Math.pow((Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)), 0.5);
        return result;
    }

    private void distanceCalculation() {
        for (int i = 0; i < startingDistanceData.length; i++) {
            startingDistanceData[i] = euclideanDistance(startingPoint[0], startingPoint[1], DistanceDataX[i], DistanceDataY[i]);
//            System.out.println((0) + "," + (i + 1) + " = " + startingDistanceData[i]);
        }

//        System.out.println("=================================");
        for (int i = 0; i < DistanceDataX.length; i++) {
            for (int j = 0; j < DistanceDataY.length; j++) {
                DistanceData[i][j] = euclideanDistance(DistanceDataX[i], DistanceDataY[i], DistanceDataX[j], DistanceDataY[j]);
//                    System.out.println((i + 1 ) + "," + (j + 1) + " = " + DistanceData[i][j]);
            }
        }
    }

    private void totalDistance() {
        totalDistance += startingDistanceData[Sequence[1] - 1];

        for (int i = 1; i < Sequence.length; i++) {
//            System.out.println(Sequence[i]);
            if (i < pointSum) {
                totalDistance += DistanceData[Sequence[i] - 1][Sequence[i + 1] - 1];
//            System.out.println(DistanceData[Sequence[i] - 1][Sequence[i + 1] - 1]);
            } else {
                totalDistance += startingDistanceData[Sequence[i] - 1];
//             System.out.println(startingDistanceData[Sequence[i] - 1]);
            }
        }
        System.out.println("totalDistance : " + totalDistance);
    }

    private void setSquence() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("隨機順序請輸入 : 0");
        System.out.println("手動順序請輸入 : 1");
        System.out.print("請輸入 : ");
        String scan = scanner.nextLine();

        if (scan.equals("0")) {
            int ran;
            Random r = new Random();
            for (int i = 1; i < Sequence.length; i++) {
                Sequence[0] = (Integer.parseInt(STxt[1]) - 1);
                ran = r.nextInt(Integer.parseInt(STxt[0]));
                Sequence[i] = ran;
                for (int j = 0; j < i; j++) {
                    if (Sequence[j] == ran) {
                        i -= 1;
                    }
                }
            }

            System.out.print("Sequence = ");
            for (int i = 0; i < Sequence.length; i++) {
                System.out.print(Sequence[i] + "\t");
            }
            System.out.println();
        } else if (scan.equals("1")) {
            Sequence[0] = (Integer.parseInt(STxt[1]) - 1);
            System.out.println("原點 : " + Sequence[0]);
            System.out.println("請輸入路徑順序 : 1 ~ " + pointSum);
            for (int i = 1; i < Sequence.length; i++) {
                Sequence[i] = (scanner.nextInt());
            }
        } else {
            System.out.println("請輸入正確的選項或正確的檔案格式!!");
        }
    }

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        DistanceCalculation DC = new DistanceCalculation();
        DC.readTxt();
        DC.distanceCalculation();
        DC.setSquence();
        DC.totalDistance();
    }
}
