using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.IO;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Get the Synonym word of the file to the values is compared with another file.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */

namespace CompareTxt
{
    class Program
    {
        static void Main(string[] args)
        {
            Program CT = new Program();
            Console.WriteLine("程式已開始啟動，將會顯示進入以及離開訊息。");
            //CT.NoRepeatJpg();
            CT.CompareTxt();
            CT.CompareTxt2();
            Console.Read();
        }
        private void CompareTxt()
        {
            Console.WriteLine("進入CompareTxt");

            //路徑
            string TxtCaltechPath = @"../../../File/Caltech101Results.txt";
            string TxtComparejpgTotal1Path = @"../../../File/jpgFilterList.txt";
            string NewTxtPath = @"../../../File/CompareTxt1.csv";
            string NewTxtPath2 = @"../../../File/ComparejpgTotal1.csv";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, false);
            StreamWriter sw2 = new StreamWriter(NewTxtPath2, false);
            sw.WriteLine("name1,jpgName,local,name2");
            sw2.WriteLine("japName,total,success,unsuccess");

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtCaltechPath);
            string[] STxt = TxtAll.Split(',', '\n');

            string TxtAll2 = File.ReadAllText(TxtComparejpgTotal1Path);
            string[] SjpgTotal1 = TxtAll2.Split(',', '\n');

            //將TXT裡面的資料 分四份存入四個陣列裡面
            string[] name1 = new string[(STxt.Length / 5)];
            int name1index = 0;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name1[i] = STxt[name1index];
                name1index += 5;
            }
            string[] jpg = new string[(STxt.Length / 5)];
            int jpgindex = 1;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                jpg[i] = STxt[jpgindex];
                jpgindex += 5;
            }
            string[] local = new string[(STxt.Length / 5)];
            int localindex = 2;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                local[i] = STxt[localindex];
                localindex += 5;
            }
            string[] name2 = new string[(STxt.Length / 5)];
            int name2index = 3;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name2[i] = STxt[name2index];
                name2index += 5;
            }

            //計算成功與失敗
            int success = 0;
            int unsuccess = 0;

            //計算每個jpg成功的次數與失敗的次數
            int[] jpgsuccess = new int[SjpgTotal1.Length];
            int[] jpgunsuccess = new int[SjpgTotal1.Length];

            //將Txt的name1與name2進行比對，並且jpg需求相等。
            for (int i = 0; i < name1.Length; i++)
            {
                if (name1[i].Trim() == name2[i].Trim())
                {
                    sw.Write(name1[i].Trim() + ",");
                    sw.Write(jpg[i].Trim() + ",");
                    sw.Write(local[i].Trim() + ",");
                    sw.Write(name2[i].Trim() + ",");
                    sw.Write("True");
                    sw.WriteLine();

                    //計算jpg成功次數
                    for (int j = 0; j < SjpgTotal1.Length; j++)
                    {
                        if (jpg[i].Trim() == SjpgTotal1[j].Trim())
                        {
                            jpgsuccess[j]++;
                        }
                    }

                    success += 1;
                }
                else
                {
                    sw.Write(name1[i].Trim() + ",");
                    sw.Write(jpg[i].Trim() + ",");
                    sw.Write(local[i].Trim() + ",");
                    sw.Write(name2[i].Trim() + ",");
                    sw.Write("False");
                    sw.WriteLine();

                    //計算jpg失敗次數
                    for (int j = 0; j < SjpgTotal1.Length; j++)
                    {
                        if (jpg[i].Trim() == SjpgTotal1[j].Trim())
                        {
                            jpgunsuccess[j]++;
                        }
                    }

                    unsuccess += 1;
                }
            }

            //將jpg成功與失敗統計寫入sw2
            int jpgsuccesstotal = 0;
            int jpgunsuccesstotal = 0;
            for (int i = 0; i < (SjpgTotal1.Length-1); i++)
            {
                sw2.Write(SjpgTotal1[i].Trim() + ",");
                sw2.Write((jpgsuccess[i] + jpgunsuccess[i]) + ",");
                sw2.Write((jpgsuccess[i]) + ",");
                sw2.WriteLine((jpgunsuccess[i]));
                jpgsuccesstotal += jpgsuccess[i];
                jpgunsuccesstotal += jpgunsuccess[i];
            }
            sw2.Write((jpgsuccesstotal + jpgunsuccesstotal) + ",");
            sw2.Write((jpgsuccesstotal) + ",");
            sw2.Write((jpgunsuccesstotal));

            //將比對成功與失敗數量寫入sw
            sw.Write((success + unsuccess) + ",");
            sw.Write((success) + ",");
            sw.Write((unsuccess));
            

            sw.Close();
            sw2.Close();
            //Console.WriteLine("比對總數：" + (success + unsuccess));
            //Console.WriteLine("比對成功：" + (success));
            //Console.WriteLine("比對失敗：" + (unsuccess));

            Console.WriteLine("離開CompareTxt");
        }
        private void NoRepeatJpg()
        {
            Console.WriteLine("進入NoRepeatJpg");
            //路徑
            string TxtCaltechPath = @"../../../File/Caltech101Results.txt";
            string NewTxtPath = @"../../../File/jpgFilterList.txt";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, false);

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtCaltechPath);
            string[] STxt = TxtAll.Split(',', '\n');

            //將TXT裡面的資料 分四份存入四個陣列裡面
            string[] name1 = new string[(STxt.Length / 5)];
            int name1index = 0;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name1[i] = STxt[name1index];
                name1index += 5;
            }
            string[] jpg = new string[(STxt.Length / 5)];
            int jpgindex = 1;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                jpg[i] = STxt[jpgindex];
                jpgindex += 5;
            }
            string[] local = new string[(STxt.Length / 5)];
            int localindex = 2;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                local[i] = STxt[localindex];
                localindex += 5;
            }
            string[] name2 = new string[(STxt.Length / 5)];
            int name2index = 3;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name2[i] = STxt[name2index];
                name2index += 5;
            }

            //儲存不重複的jpg
            string[] Filterjpg = new string[jpg.Length];
            bool chk = false;
            int count = 0;
            for (int i = 0; i < jpg.Length; i++)
            {
                chk = false;
                for (int j = 0; j < Filterjpg.Length; j++)
                {
                    if (jpg[i].Trim() == Filterjpg[j])
                    {
                        chk = true;
                    }
                }
                if (chk == false)
                {
                    Filterjpg[count] = jpg[i].Trim();
                    sw.WriteLine(jpg[i].Trim());
                    count++;
                }
            }


            sw.Close();
            Console.WriteLine("離開NoRepeatJpg");
        }
        private void CompareTxt2()
        {
            Console.WriteLine("進入CompareTxt2");

            string TxtCaltechPath = @"../../../File/Caltech101Results.txt";
            string TxtComparejpgTotal1Path = @"../../../File/jpgFilterList.txt";
            string NewTxtPath = @"../../../File/CompareTxt2.csv";
            string NewTxtPath2 = @"../../../File/ComparejpgTotal2.csv";
            string SynonymPath = @"../../../File/Synonym word";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, false);
            StreamWriter sw2 = new StreamWriter(NewTxtPath2, false);
            sw.WriteLine("name1,jpgName,local,name2");
            sw2.WriteLine("japName,total,success,unsuccess");

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtCaltechPath);
            string[] STxt = TxtAll.Split(',', '\n');

            string TxtAll2 = File.ReadAllText(TxtComparejpgTotal1Path);
            string[] SjpgTotal1 = TxtAll2.Split(',', '\n');

            //將TXT裡面的資料 分四份存入四個陣列裡面
            string[] name1 = new string[(STxt.Length / 5)];
            int name1index = 0;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name1[i] = STxt[name1index];
                name1index += 5;
            }
            string[] jpg = new string[(STxt.Length / 5)];
            int jpgindex = 1;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                jpg[i] = STxt[jpgindex];
                jpgindex += 5;
            }
            string[] local = new string[(STxt.Length / 5)];
            int localindex = 2;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                local[i] = STxt[localindex];
                localindex += 5;
            }
            string[] name2 = new string[(STxt.Length / 5)];
            int name2index = 3;
            for (int i = 0; i < STxt.Length / 5; i++)
            {
                name2[i] = STxt[name2index];
                name2index += 5;
            }

            //計算成功與失敗
            int success = 0;
            int unsuccess = 0;

            //計算每個jpg成功的次數與失敗的次數
            int[] jpgsuccess = new int[SjpgTotal1.Length];
            int[] jpgunsuccess = new int[SjpgTotal1.Length];

            //讀取資料夾內Synonym的檔案
            String[] SynonymWordCollection;
            SynonymWordCollection = Directory.GetFiles(SynonymPath, "*.txt");

            //將Txt的name1與name2進行比對，並且jpg需求相等。
            for (int i = 0; i < name1.Length; i++)
            {
                if (name1[i].Trim() == name2[i].Trim())
                {
                    sw.Write(name1[i].Trim() + ",");
                    sw.Write(jpg[i].Trim() + ",");
                    sw.Write(local[i].Trim() + ",");
                    sw.Write(name2[i].Trim() + ",");
                    sw.Write("True");
                    sw.WriteLine();

                    //計算jpg成功次數
                    for (int j = 0; j < SjpgTotal1.Length; j++)
                    {
                        if (jpg[i].Trim() == SjpgTotal1[j].Trim())
                        {
                            jpgsuccess[j]++;
                        }
                    }

                    success += 1;
                }
                else
                {
                    for (int j = 0; j < SynonymWordCollection.Length; j++)
                    {
                        Boolean insuccess = false;
                        insuccess = false;
                        if (name1[i].Trim() == Path.GetFileNameWithoutExtension(SynonymWordCollection[j]))
                        {
                            string SynonymWordTxt = File.ReadAllText(SynonymWordCollection[j]);
                            string[] SSynonymWordTxt = SynonymWordTxt.Split('\n');

                            for (int SSWTcount = 0; SSWTcount < SSynonymWordTxt.Length; SSWTcount++)
                            {
                                if (name2[i].Trim() == SSynonymWordTxt[SSWTcount].Trim())
                                {
                                    sw.Write(name1[i].Trim() + ",");
                                    sw.Write(jpg[i].Trim() + ",");
                                    sw.Write(local[i].Trim() + ",");
                                    sw.Write(name2[i].Trim() + ",");
                                    sw.Write("True");
                                    sw.WriteLine();

                                    //計算jpg成功次數
                                    for (int l = 0; l < SjpgTotal1.Length; l++)
                                    {
                                        if (jpg[i].Trim() == SjpgTotal1[l].Trim())
                                        {
                                            jpgsuccess[l]++;
                                        }
                                    }

                                    success += 1;
                                    insuccess = true;
                                }
                            }
                            if (insuccess == false)
                            {
                                sw.Write(name1[i].Trim() + ",");
                                sw.Write(jpg[i].Trim() + ",");
                                sw.Write(local[i].Trim() + ",");
                                sw.Write(name2[i].Trim() + ",");
                                sw.Write("False");
                                sw.WriteLine();

                                //計算jpg失敗次數
                                for (int l = 0; l < SjpgTotal1.Length; l++)
                                {
                                    if (jpg[i].Trim() == SjpgTotal1[l].Trim())
                                    {
                                        jpgunsuccess[l]++;
                                    }
                                }

                                unsuccess += 1;
                            }
                        }
                    }
                }
            }

            //將jpg成功與失敗統計寫入sw2
            int jpgsuccesstotal = 0;
            int jpgunsuccesstotal = 0;
            for (int i = 0; i < (SjpgTotal1.Length - 1); i++)
            {
                sw2.Write(SjpgTotal1[i].Trim() + ",");
                sw2.Write((jpgsuccess[i] + jpgunsuccess[i]) + ",");
                sw2.Write((jpgsuccess[i]) + ",");
                sw2.WriteLine((jpgunsuccess[i]));
                jpgsuccesstotal += jpgsuccess[i];
                jpgunsuccesstotal += jpgunsuccess[i];
            }
            sw2.Write((jpgsuccesstotal + jpgunsuccesstotal) + ",");
            sw2.Write((jpgsuccesstotal) + ",");
            sw2.Write((jpgunsuccesstotal));

            //將比對成功與失敗數量寫入sw
            sw.Write((success + unsuccess) + ",");
            sw.Write((success) + ",");
            sw.Write((unsuccess));


            sw.Close();
            sw2.Close();
            //Console.WriteLine("比對總數：" + (success + unsuccess));
            //Console.WriteLine("比對成功：" + (success));
            //Console.WriteLine("比對失敗：" + (unsuccess));

            Console.WriteLine("離開CompareTxt2");
            
        }
    }
}
