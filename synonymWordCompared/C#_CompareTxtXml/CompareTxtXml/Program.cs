using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Xml;
using System.IO;
using Excel = Microsoft.Office.Interop.Excel;
using System.Drawing;

/********************************
 * <p>Title: School_Program </p>
 * <p>Description: Get the Values of xml file is compared with another file.</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: Cheng Shiu University </p>
 * @author Kuo, Yu-Cheng
 * @version 1.0
 */

namespace CompareTxtXml
{
    class Program
    {
        //路徑
        string XmlPath = @"../../../Readxml/Annotations";
        string LikeNamePath = @"../../../Readxml/LikeName";
        string TxtPascalPath = @"../../../Readxml/PASCAL2007Results.txt";

        static void Main(string[] args)
        {
            Program Px = new Program();
            Console.WriteLine("程式已開始啟動，將會顯示進入以及離開訊息。");
            Px.CompareTxtXml();
            Px.Filterfailname();
            Px.CompareTxtXml2();
            Px.Filterfailname2();
            Console.Read();
        }
        private void CompareTxtXml()
        {
            Console.WriteLine("進入CompareTxtXml");

            //存取未比對成功的name
            int failnamecount = 0;
            string[] failname = new string[3350];

            //路徑
            string NewTxtPath = @"../../../Readxml/WriteCompare.txt";
            string NewTxtPath2 = @"../../../Readxml/Failnamelist.txt";
            string NewTxtPath3 = @"../../../Readxml/Allnamelist.txt";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, true);
            StreamWriter sw2 = new StreamWriter(NewTxtPath2, true);
            StreamWriter sw3 = new StreamWriter(NewTxtPath3, true);

            //計算搜尋檔案的總數量
            int unsuccesstotal = 0;
            int successtotal = 0;

            //讀取資料夾內xml的檔案
            String[] FileCollection;
            FileCollection = Directory.GetFiles(XmlPath, "*.xml");

            //讀取資料夾內LikeName的檔案
            String[] LikeNameCollection;
            LikeNameCollection = Directory.GetFiles(LikeNamePath, "*.txt");

            //重複執行資料夾中讀取到的檔案
            for (int FileCount = 0; FileCount < FileCollection.Length; FileCount++)
            {

                //txt轉成陣列
                string TxtAll = File.ReadAllText(TxtPascalPath);
                string[] STxt = TxtAll.Split(',', '\n');

                //判別name次數所需使用
                XmlTextReader readxml = new XmlTextReader(FileCollection[FileCount]);

                //xml存入陣列所需使用
                XmlTextReader readxml2 = new XmlTextReader(FileCollection[FileCount]);

                //將TXT裡面的資料 分三份存入三個陣列裡面
                string[] jpg = new string[(STxt.Length / 4)];
                int jpgindex = 0;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    jpg[i] = STxt[jpgindex];
                    jpgindex += 4;
                }
                string[] location = new string[(STxt.Length / 4)];
                int locationindex = 1;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    location[i] = STxt[locationindex];
                    locationindex += 4;
                }
                string[] name = new string[(STxt.Length / 4)];
                int nameindex = 2;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    name[i] = STxt[nameindex];
                    nameindex += 4;

                }

                //印出三個陣列內容
                /*for (int i = 0; i < jpg.Length; i++)
                {
                    Console.WriteLine(jpg[20]);
                    Console.WriteLine(location[0]);
                    Console.WriteLine(name[0]);
                }*/
                //            

                //xml存入陣列所需使用
                Boolean inObject = false;
                Boolean inObject2 = false;
                Boolean infilename = false;

                //計算xml在object裡面的name有幾個
                int count = 0;
                while (readxml.Read())
                {
                    if (readxml.Name == "object" && readxml.NodeType == XmlNodeType.Element)
                    {
                        inObject2 = true;
                    }
                    if (readxml.Name == "name" && inObject2 && readxml.NodeType == XmlNodeType.Element)
                    {
                        count += 1;
                    }
                }

                //將xml裡面的filename && name 存入兩個陣列裡面
                string filename = "";
                string[] name2 = new string[count];
                int count2 = 0;
                count2 = 0;
                while (readxml2.Read())
                {
                    if (readxml2.Name == "filename" && readxml2.NodeType == XmlNodeType.Element)
                    {
                        if (readxml2.Read())
                        {
                            filename = readxml2.Value;
                            infilename = true;
                        }
                    }
                    if (readxml2.Name == "object" && readxml2.NodeType == XmlNodeType.Element && infilename)
                    {
                        inObject = true;
                    }
                    if (readxml2.Name == "name" && inObject && readxml2.NodeType == XmlNodeType.Element)
                    {
                        if (readxml2.Read())
                        {
                            name2[count2] = readxml2.Value.Trim();
                            count2 += 1;
                        }
                    }
                }

                //印出兩個陣列內容
                /*
                for (int i = 0; i < name2.Length; i++)
                {
                    Console.WriteLine(filename);
                    Console.WriteLine(name2[i]);
                }
    //            */

                //GetFileName
                string result = Path.GetFileName(FileCollection[FileCount]);

                //將txt的陣列以及xml的陣列進行比對並寫入新的txt檔案內
                Boolean unsuccess = true;
                int count3 = 0;
                for (int i = 0; i < jpg.Length; i++)
                {
                    if (jpg[i] == filename)
                    {
                        if (count3 < name2.Length)
                        {
                            if (name[i].Trim() == name2[count3])
                            {
                                /*sw.WriteLine(jpg[i]);
                                sw.WriteLine(location[i]);
                                sw.WriteLine(name[i]);*/

                                sw.WriteLine("{0}   true", result);
                                successtotal += 1;
                                unsuccess = false;
                                sw3.WriteLine(name2[count3]);

                                //Console.WriteLine(jpg[i]);
                                //Console.WriteLine(location[i]);
                                //Console.WriteLine(name[i]);

                                count3 += 1;
                            }
                        }
                    }
                }
                if (unsuccess)
                {
                    failname[failnamecount] = name2[count3];
                    sw.WriteLine("{0}   false  {1}", result, failname[failnamecount]);
                    sw2.WriteLine(failname[failnamecount]);
                    sw3.WriteLine(failname[failnamecount]);

                    failnamecount += 1;
                    unsuccesstotal += 1;
                }
                count3 = 0;
                unsuccess = true;
            }

            //輸出計算尋找資料的結果
            Console.WriteLine("總共搜尋了：" + (successtotal + unsuccesstotal) + "筆xml檔案");
            Console.WriteLine("已找到：" + successtotal + "筆xml檔案");
            Console.WriteLine("未找到：" + unsuccesstotal + "筆xml檔案");

            sw.WriteLine("總共搜尋了：" + (successtotal + unsuccesstotal) + "筆xml檔案");
            sw.WriteLine("已找到：" + successtotal + "筆xml檔案");
            sw.WriteLine("未找到：" + unsuccesstotal + "筆xml檔案");

            sw.Close();
            sw2.Close();
            sw3.Close();

            Console.WriteLine("離開CompareTxtXml");
        }
        private void Filterfailname()
        {
            Console.WriteLine("進入Filterfailname");

            //路徑
            string TxtPath = @"../../../Readxml/Failnamelist.txt";
            string NewTxtPath = @"../../../Readxml/Filterfailname.txt";
            string TxtPath2 = @"../../../Readxml/Allnamelist.txt";
            string NewTxtPath2 = @"../../../Readxml/AllFiltername.txt";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, true);
            StreamWriter sw2 = new StreamWriter(NewTxtPath2, true);

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtPath);
            string[] STxt = TxtAll.Split(',', '\n');
            string TxtAll2 = File.ReadAllText(TxtPath2);
            string[] STxt2 = TxtAll2.Split(',', '\n');

            //儲存不重複的字串
            string[] FilterSTxt = new string[STxt.Length];
            //List<string> FilterStxt = new List<string>();
            bool chk = false;
            int count = 0;
            for (int i = 0; i < FilterSTxt.Length; i++)
            {
                chk = false;
                for (int j = 0; j < FilterSTxt.Length; j++)
                {
                    if (STxt[i].Trim() == FilterSTxt[j])
                    {
                        chk = true;
                    }
                }
                if (chk == false)
                {
                    FilterSTxt[count] = STxt[i].Trim();
                    count++;
                }
            }
            
            //儲存不重複的字串2
            string[] FilterSTxt2 = new string[STxt2.Length];
            bool chk2 = false;
            int count2 = 0;
            for (int i = 0; i < STxt2.Length; i++)
            {
                chk2 = false;
                for (int j = 0; j < FilterSTxt2.Length; j++)
                {
                    if (STxt2[i].Trim() == FilterSTxt2[j])
                    {
                        chk2 = true;
                    }
                }
                if (chk2 == false)
                {
                    FilterSTxt2[count2] = STxt2[i].Trim();
                    count2++;
                }
            }

            //寫入TXT裡面
            for (int i = 0; i < count; i++)
            {
                sw.WriteLine(FilterSTxt[i]);
            }
            for (int i = 0; i < count2; i++)
            {
                sw2.WriteLine(FilterSTxt2[i]);
            }
            sw.Close();
            sw2.Close();
            Console.WriteLine("離開Filterfailname");
        }
        private void CompareTxtXml2()
        {
            Console.WriteLine("進入CompareTxtXml2");

            //存取未比對成功的name
            int failnamecount = 0;
            string[] failname = new string[3350];

            //路徑
            string XmlPath = @"../../../Readxml/Annotations";
            string NewTxtPath = @"../../../Readxml/WriteCompare2.txt";
            string NewTxtPath2 = @"../../../Readxml/Failnamelist2.txt";

            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, true);
            StreamWriter sw2 = new StreamWriter(NewTxtPath2, true);

            //計算搜尋檔案的總數量
            int unsuccesstotal = 0;
            int successtotal = 0;

            //讀取資料夾內xml的檔案
            String[] FileCollection;
            FileCollection = Directory.GetFiles(XmlPath, "*.xml");

            //讀取資料夾內LikeName的檔案
            String[] LikeNameCollection;
            LikeNameCollection = Directory.GetFiles(LikeNamePath, "*.txt");

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtPascalPath);
            string[] STxt = TxtAll.Split(',', '\n');

            //重複執行資料夾中讀取到的檔案
            for (int FileCount = 0; FileCount < FileCollection.Length; FileCount++)
            {
                //判別name次數所需使用
                XmlTextReader readxml = new XmlTextReader(FileCollection[FileCount]);

                //xml存入陣列所需使用
                XmlTextReader readxml2 = new XmlTextReader(FileCollection[FileCount]);

                //將TXT裡面的資料 分三份存入三個陣列裡面
                string[] jpg = new string[(STxt.Length / 4)];
                int jpgindex = 0;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    jpg[i] = STxt[jpgindex];
                    jpgindex += 4;
                }
                string[] location = new string[(STxt.Length / 4)];
                int locationindex = 1;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    location[i] = STxt[locationindex];
                    locationindex += 4;
                }
                string[] name = new string[(STxt.Length / 4)];
                int nameindex = 2;
                for (int i = 0; i < STxt.Length / 4; i++)
                {
                    name[i] = STxt[nameindex];
                    nameindex += 4;

                }

                //印出三個陣列內容
                /*for (int i = 0; i < jpg.Length; i++)
                {
                    Console.WriteLine(jpg[20]);
                    Console.WriteLine(location[0]);
                    Console.WriteLine(name[0]);
                }*/
                //            

                //xml存入陣列所需使用
                Boolean inObject = false;
                Boolean inObject2 = false;
                Boolean infilename = false;
                Boolean inLikeName = false;

                //計算xml在object裡面的name有幾個
                int count = 0;
                while (readxml.Read())
                {
                    if (readxml.Name == "object" && readxml.NodeType == XmlNodeType.Element)
                    {
                        inObject2 = true;
                    }
                    if (readxml.Name == "name" && inObject2 && readxml.NodeType == XmlNodeType.Element)
                    {
                        count += 1;
                    }
                }

                //將xml裡面的filename && name 存入兩個陣列裡面
                string filename = "";
                string[] name2 = new string[count];
                int count2 = 0;
                count2 = 0;
                while (readxml2.Read())
                {
                    if (readxml2.Name == "filename" && readxml2.NodeType == XmlNodeType.Element)
                    {
                        if (readxml2.Read())
                        {
                            filename = readxml2.Value;
                            infilename = true;
                        }
                    }
                    if (readxml2.Name == "object" && readxml2.NodeType == XmlNodeType.Element && infilename)
                    {
                        inObject = true;
                    }
                    if (readxml2.Name == "name" && inObject && readxml2.NodeType == XmlNodeType.Element)
                    {
                        if (readxml2.Read())
                        {
                            name2[count2] = readxml2.Value.Trim();
                            count2 += 1;
                        }
                    }
                }

                //印出兩個陣列內容
                /*
                for (int i = 0; i < name2.Length; i++)
                {
                    Console.WriteLine(filename);
                    Console.WriteLine(name2[i]);
                }
    //            */

                //GetFileName
                string result = Path.GetFileName(FileCollection[FileCount]);

                //將txt的陣列以及xml的陣列進行比對並寫入新的txt檔案內
                Boolean unsuccess = true;
                int count3 = 0;
                for (int i = 0; i < jpg.Length; i++)
                {
                    if (jpg[i] == filename)
                    {
                        if (count3 < name2.Length)
                        {
                            if (name[i].Trim() == name2[count3])
                            {
                                /*sw.WriteLine(jpg[i]);
                                sw.WriteLine(location[i]);
                                sw.WriteLine(name[i]);*/

                                sw.WriteLine("{0} true ", result);
                                successtotal += 1;
                                unsuccess = false;

                                //Console.WriteLine(jpg[i]);
                                //Console.WriteLine(location[i]);
                                //Console.WriteLine(name[i]);

                                count3 += 1;
                            }
                        }
                    }
                }
                if (unsuccess)
                {
                    failname[failnamecount] = name2[count3];

                    for (int i = 0; i < LikeNameCollection.Length; i++)
                    {
                        if (name2[count3].Trim() == Path.GetFileNameWithoutExtension(LikeNameCollection[i]))
                        {
                            string LikeNameTxt = File.ReadAllText(LikeNameCollection[i]);
                            string[] SLikeNameTxt = LikeNameTxt.Split('\n', ' ');
                            foreach (string S in SLikeNameTxt)
                            {
                                for (int j = 0; j < jpg.Length; j++)
                                {
                                    if (jpg[j] == filename)
                                    {
                                        if (S.Trim() == name[j].Trim())
                                        {
                                            sw.Write("{0} true  {1}", result, failname[failnamecount] + " ");
                                            sw.Write(jpg[j] + ",");
                                            sw.Write(location[j] + ",");
                                            sw.Write(name[j] + "\n");
                                            inLikeName = true;
                                            successtotal += 1;
                                            unsuccesstotal -= 1;
                                        }
                                    }
                                }
                            }
                            if (inLikeName == false)
                            {
                                sw2.WriteLine(failname[failnamecount]);
                                sw.WriteLine("{0} false {1}", result, failname[failnamecount]);
                            }
                        }
                    }
                    failnamecount += 1;
                    unsuccesstotal += 1;
                }
                count3 = 0;
                unsuccess = true;
            }

            //輸出計算尋找資料的結果
            Console.WriteLine("總共搜尋了：" + (successtotal + unsuccesstotal) + "筆xml檔案");
            Console.WriteLine("已找到：" + successtotal + "筆xml檔案");
            Console.WriteLine("未找到：" + unsuccesstotal + "筆xml檔案");

            sw.WriteLine("總共搜尋了：" + (successtotal + unsuccesstotal) + "筆xml檔案");
            sw.WriteLine("已找到：" + successtotal + "筆xml檔案");
            sw.WriteLine("未找到：" + unsuccesstotal + "筆xml檔案");

            sw.Close();
            sw2.Close();
            Console.WriteLine("離開CompareTxtXml2");
        }
        private void Filterfailname2()
        {
            Console.WriteLine("進入Filterfailname2");
            //路徑
            string TxtPath = @"../../../Readxml/AllFiltername.txt";
            string TxtPath2 = @"../../../Readxml/Failnamelist.txt";
            string TxtPath3 = @"../../../Readxml/Failnamelist2.txt";
            string TxtPath4 = @"../../../Readxml/Allnamelist.txt";

            //txt轉成陣列
            string TxtAll = File.ReadAllText(TxtPath);
            string[] STxt = TxtAll.Split(',', '\n');
            string TxtAll2 = File.ReadAllText(TxtPath2);
            string[] STxt2 = TxtAll2.Split(',', '\n');
            string TxtAll3 = File.ReadAllText(TxtPath3);
            string[] STxt3 = TxtAll3.Split(',', '\n');
            string TxtAll4 = File.ReadAllText(TxtPath4);
            string[] STxt4 = TxtAll4.Split(',', '\n');

            //計算TXT的數量
            int[] count1 = new int[STxt.Length];
            int[] count2 = new int[STxt.Length];
            int[] count3 = new int[STxt.Length];

            //計算符合TxtPath的TxtPath2各項目數量
            for (int i = 0; i < STxt.Length-2; i++)
            {
                for (int j = 0; j < STxt2.Length-2; j++)
                {
                    if (STxt[i].Trim() == STxt2[j].Trim())
                    {
                        count1[i] += 1;
                    }
                }
                for (int k = 0; k < STxt3.Length-2; k++)
                {
                    if (STxt[i].Trim() == STxt3[k].Trim())
                    {
                        count2[i] += 1;
                    }
                }
                for (int m = 0; m < STxt4.Length-2; m++)
                {
                    if (STxt[i].Trim() == STxt4[m].Trim())
                    {
                        count3[i] += 1;
                    }
                }
            }

            // 設定儲存檔名，不用設定副檔名，系統自動判斷 excel 版本，產生 .xls 或 .xlsx 副檔名
            string str = System.Environment.CurrentDirectory.Remove(Encoding.Default.GetByteCount(System.Environment.CurrentDirectory) - 23);
            string pathFile = str + @"Readxml\Filterfailname2";
            Excel.Application excelApp;
            Excel._Workbook wBook;
            Excel._Worksheet wSheet;
            Excel.Range wRange;
            // 開啟一個新的應用程式
            excelApp = new Excel.Application();

            // 讓Excel文件可見
            excelApp.Visible = true;

            // 停用警告訊息
            excelApp.DisplayAlerts = false;

            // 加入新的活頁簿
            excelApp.Workbooks.Add(Type.Missing);

            // 引用第一個活頁簿
            wBook = excelApp.Workbooks[1];

            // 設定活頁簿焦點
            wBook.Activate();
            try
            {
                // 引用第一個工作表
                wSheet = (Excel._Worksheet)wBook.Worksheets[1];

                // 命名工作表的名稱
                wSheet.Name = "Filterfailname2";

                // 設定第1列資料
                excelApp.Cells[1, 1] = "使用同義字前";
                excelApp.Cells[1, 2] = "名稱";
                excelApp.Cells[1, 3] = "總數量";
                excelApp.Cells[1, 4] = "已發現數量";
                excelApp.Cells[1, 5] = "未發現數量";
                excelApp.Cells[1, 6] = "發現的百分比";
                excelApp.Cells[1, 7] = "使用同義字後";
                excelApp.Cells[1, 8] = "名稱";
                excelApp.Cells[1, 9] = "總數量";
                excelApp.Cells[1, 10] = "已發現數量";
                excelApp.Cells[1, 11] = "未發現數量";
                excelApp.Cells[1, 12] = "發現的百分比";

                //寫入EXCEL
                for (int i = 0; i < STxt.Length-2; i++)
                {
                    excelApp.Cells[i + 2, 2] = STxt[i].Trim();
                    excelApp.Cells[i + 2, 3] = count3[i];
                    excelApp.Cells[i + 2, 4] = ("=C"+ (i + 2) + ("-E") + ( i + 2));
                    excelApp.Cells[i + 2, 5] = count1[i];
                    excelApp.Cells[i + 2, 6] = ("=D" + (i + 2) + ("/C") + (i + 2) + ("*100"));

                    excelApp.Cells[i + 2, 8] = STxt[i].Trim();
                    excelApp.Cells[i + 2, 9] = count3[i];
                    excelApp.Cells[i + 2, 10] = ("=I" + (i + 2) + ("-K") + (i + 2));
                    excelApp.Cells[i + 2, 11] = count2[i];
                    excelApp.Cells[i + 2, 12] = ("=J" + (i + 2) + ("/I") + (i + 2) + ("*100"));
                }

                // 設定第1列顏色
                /*wRange = wSheet.Range[wSheet.Cells[1, 1], wSheet.Cells[1, 2]];
                wRange.Select();
                wRange.Font.Color = ColorTranslator.ToOle(Color.White);
                wRange.Interior.Color = ColorTranslator.ToOle(Color.DimGray);*/

                // 自動調整欄寬
                wRange = wSheet.Range[wSheet.Cells[1,1], wSheet.Cells[100, 100]];
                wRange.Select();
                wRange.Columns.AutoFit();
                try
                {
                    //另存活頁簿
                    /*wBook.SaveAs(pathFile);
                    Console.WriteLine("儲存文件於 " + Environment.NewLine + pathFile);*/
                }
                catch (Exception ex)
                {
                    Console.WriteLine("儲存檔案出錯，檔案可能正在使用" + Environment.NewLine + ex.Message);
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("產生報表時出錯！" + Environment.NewLine + ex.Message);
            }
            //關閉活頁簿
            wBook.Close(true,pathFile , Type.Missing);
            //關閉Excel
            excelApp.Quit();
            //釋放Excel資源
            System.Runtime.InteropServices.Marshal.ReleaseComObject(excelApp);
            wBook = null;
            wSheet = null;
            wRange = null;
            excelApp = null;
            GC.Collect();
            
            Console.WriteLine("離開Filterfailname2");
        }
    }
}
