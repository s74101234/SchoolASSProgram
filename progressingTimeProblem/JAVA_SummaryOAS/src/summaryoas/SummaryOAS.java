/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package summaryoas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Read values of the file is compared with another file.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */
public class SummaryOAS {

    /**
     * @param args the command line arguments
     */
    
    private String readOASPath = ".\\Files\\OAS Best UB2.txt";
    private String readSummaryPath = ".\\Files\\mySummary.txt";
    private String instanceTemp = "";
    private String[] SOAS;
    private String[] SSummary;
    private ArrayList<String> instance = new ArrayList<String>();
    private ArrayList<String> bestR = new ArrayList<String>() ;
    private int FristOASTitleCount = 10;
    private int FristSummaryTitleCount = 19;
    
    private void readOAS() throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader(readOASPath);
        BufferedReader br = new BufferedReader(fr);
        String TxtAll = "", eachLine = "";
            while ((eachLine = br.readLine()) != null) {
                TxtAll += eachLine + "\n";
            }                     
        SOAS = TxtAll.split(" |\n|\t");
        
        for(int i = FristOASTitleCount ; i < SOAS.length ; i++ )
        {
            if((i - FristOASTitleCount) % 45 ==0 )
            {
                instanceTemp = ((SOAS[i].substring(0,SOAS[i].length()-5)) + "-" +
                        (SOAS[i].substring(SOAS[i].length()-1,SOAS[i].length())) + "-"  + 
                        SOAS[i+1].substring(1,SOAS[i+1].length()) + "-");
                
                for(int j = 6 ; j <= 44 ; j+=4)
                {
                    instance.add(instanceTemp + SOAS[i+j]);
                    bestR.add(SOAS[i+j+1]);
                }
                
//                for(int j = 0 ; j < instance.size() ; j++)
//                {
//                    System.out.println(instance.get(j));
//                    System.out.println(bestR.get(j));
//                }
            }
        }
    }
    private void readSummary() throws FileNotFoundException, IOException
    {
        FileReader fr = new FileReader(readSummaryPath);
        BufferedReader br = new BufferedReader(fr);
        String TxtAll = "", eachLine = "";
            while ((eachLine = br.readLine()) != null) {
                TxtAll += eachLine + "\n";
            }                     
        SSummary = TxtAll.split(",|\n|\"");
        
        for(int i = FristSummaryTitleCount ; i < SSummary.length ; i++)
        {
            if((i - FristSummaryTitleCount) % 9 == 0 )
            {
                System.out.println(SSummary[i]);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        SummaryOAS SOAS = new SummaryOAS();
        SOAS.readOAS();
        SOAS.readSummary();
    }
}
    