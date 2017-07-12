/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparetxt.java;

import java.io.FileWriter;//write the Txt
import java.io.FileReader;//Read the Txt
import java.io.BufferedReader;//Read Temporary
import java.io.IOException;//Detection
import java.io.*;

/**
 *
 * @author Guo Yu-Cheng
 */
public class CompareTxtJAVA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        {
            // TODO code application logic here
            CompareTxtJAVA CT = new CompareTxtJAVA();
            System.out.println("CompareTxtJAVA Starting...");
            
            CT.ReadTxt();
            CT.CompareTxt();
            CT.SynomyCompareTxt();
            CT.OupputFilesClose();
            
            System.out.println("CompareTxtJAVA Finish.");
        }
    }
    
    //Time Count
    long start, end;
    
    //Create FileWriter
    FileWriter sw1,sw2;
    
    //ReadPath
    private String TxtCaltechPath = "@../../File/Caltech101Results.txt";
    private String SynonymPath = "@../../File/Synonym word";
    
    //SW Path
    private String NewTxtPath1 = "@../../File/CompareTxt1.txt";
    private String NewTxtPath2 = "@../../File/CompareTxt2.txt";
    
    /*ReadTxtValue*/
    private String[] name1,jpg,local,name2;
    private java.io.File[] SynonymWordCollection;
    
    private void ReadTxt() throws IOException{
        {
            System.out.println("ReadTxt Starting...");
            start();
            
            //Read Caltech101Results
            FileReader fr = new FileReader(TxtCaltechPath);
            BufferedReader br = new BufferedReader(fr);
            String TxtAll = "", eachLine = "";
            while ((eachLine = br.readLine()) != null) {
                TxtAll += eachLine + "\n";
            }
            //Split TxtAll 
            String[] STxt = TxtAll.split(",|\n");
            //The STxt are saved to different arrays.
            name1 = new String[(STxt.length / 5)];
            jpg = new String[(STxt.length / 5)];
            local = new String[(STxt.length / 5)];
            name2 = new String[(STxt.length / 5)];
            int name1index = 0,jpgindex = 1,localindex = 2,name2index = 3;
            
            for (int i = 0; i < STxt.length / 5; i++) {
                name1[i] = STxt[name1index];
                name1index += 5;
                jpg[i] = STxt[jpgindex];
                jpgindex += 5;
                local[i] = STxt[localindex];
                localindex += 5;
                name2[i] = STxt[name2index];
                name2index += 5;
            };
            
            //Read file of the Synonym 
            SynonymWordCollection = new java.io.File(SynonymPath).listFiles();
            
            end();
            System.out.println(getExecutionTime()/1000);
            System.out.println("ReadTxt Finish.");
    }
}
    
    private void CompareTxt()throws IOException{
        {
            System.out.println("CompareTxt Starting...");
            start();
            //Create FileWriter
            sw1 = new FileWriter(NewTxtPath1, false);
            
            //Count success or unsuccess
            int success = 0 , unsuccess = 0;

            //Compare name1 and name2
            for (int i = 0; i < name1.length; i++) {
                if (name1[i].trim().equals(name2[i].trim())) {
                    OupputFilesOpen1(name1[i].trim() + "," + jpg[i].trim() + ","
                             +  local[i].trim() + "," + name2[i].trim() + ","
                             +  "True" + "\n" + "\n");
                    
                    success += 1;
                    
                } else {
                    OupputFilesOpen1(name1[i].trim() + "," + jpg[i].trim() + ","
                             +  local[i].trim() + "," + name2[i].trim() + ","
                             +  "false" + "\n" + "\n");
                    
                    unsuccess += 1;
                }
            }
           
            //sw1
            OupputFilesOpen1("total:" + (success + unsuccess) + "\n"
                     +  "success total:" + (success) + "\n"
                     +  "unsuccess total:" + (unsuccess) + "\n");
            
            System.out.println("total:" + (success + unsuccess));
            System.out.println("success total:" + (success));
            System.out.println("unsuccess total:" + (unsuccess));
            
            end(); 
            System.out.println(getExecutionTime()/1000);
            System.out.println("CompareTxt Finish.");
        }
    }
    
    private void SynomyCompareTxt()throws IOException{
        {
            System.out.println("SynomyCompareTxt Starting...");
            start();
            //Create FileWriter
            sw2 = new FileWriter(NewTxtPath2, false);
            
            //Count success2 or unsuccess2
            int success2 = 0 , unsuccess2 = 0;
            
            //Compare name1 and name2 of the Synonym 
            for (int i = 0; i < name1.length; i++) {
                if (name1[i].trim().equals(name2[i].trim())) {
                    OupputFilesOpen2(name1[i].trim() + "," + jpg[i].trim() + ","
                             +  local[i].trim() + "," + name2[i].trim() + ","
                             +  "True" + "\n" + "\n");

                    success2 += 1;
                } 
                else {
                    for (int j = 0; j < SynonymWordCollection.length; j++) {
                        Boolean insuccess = false;
                        insuccess = false;
                        if ((name1[i].trim() + ".txt").equals(SynonymWordCollection[j].getName())) {

                            //Read (SynonymPath + SynonymWordCollection[j].getName())
                            FileReader fr4 = new FileReader(SynonymPath +"/" +  SynonymWordCollection[j].getName());
                            BufferedReader br4 = new BufferedReader(fr4);
                            String TxtAll4 = "", eachLine4 = "";
                            while ((eachLine4 = br4.readLine()) != null) {
                                TxtAll4 += eachLine4 + "\n";
                            }
                            //Split TxtAll4
                            String[] SSynonymWordTxt = TxtAll4.split("\n");

                            for (int SSWTcount = 0; SSWTcount < SSynonymWordTxt.length; SSWTcount++) {
                                if (name2[i].trim().equals(SSynonymWordTxt[SSWTcount].trim())) {
                                    OupputFilesOpen2(name1[i].trim() + "," + jpg[i].trim() + ","
                                             +  local[i].trim() + "," + name2[i].trim() + ","
                                             +  "True" + "\n" + "\n");

                                    success2 += 1;
                                    insuccess = true;
                                }
                            }
                            if (insuccess.equals(false)) {
                                OupputFilesOpen2(name1[i].trim() + "," + jpg[i].trim() + ","
                                         +  local[i].trim() + "," + name2[i].trim() + ","
                                         +  "false" + "\n" + "\n");

                                unsuccess2 += 1;
                            }
                        }
                    }
                }
            }

            //success2 or unsuccess2
            OupputFilesOpen2("total:" + (success2 + unsuccess2) + "\n");
            OupputFilesOpen2("success total:" + (success2) + "\n");
            OupputFilesOpen2("unsuccess total:" + (unsuccess2) + "\n");
            
            System.out.println("total:" + (success2 + unsuccess2));
            System.out.println("success total:" + (success2));
            System.out.println("unsuccess total:" + (unsuccess2));
            
            end();
            System.out.println(getExecutionTime()/1000);
            System.out.println("SynomyCompareTxt Finish.");
        }
    }
    
    private void OupputFilesOpen1(String SW1value)throws IOException{
        {
            sw1.write(SW1value);
        }
    }
    
    private void OupputFilesOpen2(String SW2value)throws IOException{
        {
            sw2.write(SW2value);
        }
    }
    
    private void OupputFilesClose()throws IOException{
        {
            sw1.close();
            sw2.close();
        }
    }

    public void start(){
      start = System.currentTimeMillis();
    }

    public void end(){
      end = System.currentTimeMillis();
    }

    public long getExecutionTime(){
      return (end - start);
    }
}
