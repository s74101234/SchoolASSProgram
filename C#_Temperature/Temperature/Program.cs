using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using System.Xml;
using System.IO;
using System.Drawing;

namespace Temperature
{
    class Program
    {
        private static string XmlPath = @"../../../File/C-B0024-002.xml";
        private static string NewTxtPath = @"../../../File/result.csv";
        private static List<string> resultValue = new List<string>();


        static void Main(string[] args)
        {
            Console.WriteLine("開始執行.");
            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, false);

            XmlTextReader readxml = new XmlTextReader(XmlPath);

            int Count = 0;
            string temp = "", locationName = "";
            string[] obsTimeValue = new string[2];
            Boolean line = false;
            //Save Value
            List<string> Value1 = new List<string>();
            List<string> Value2 = new List<string>();
            List<string> Value3 = new List<string>();
            List<string> Value4 = new List<string>();
            List<string> Value5 = new List<string>();
            List<string> Value6 = new List<string>();
            List<string> Value7 = new List<string>();
            List<string> Value8 = new List<string>();
            List<string> Value9 = new List<string>();
            List<string> Value10 = new List<string>();

            //Set initial length
            Value1.Add("");
            Value2.Add("");
            Value3.Add("");
            Value4.Add("");
            Value5.Add("");
            Value6.Add("");
            Value7.Add("");
            Value8.Add("");
            Value9.Add("");
            Value10.Add("");

            //title
            sw.Write("英文locationName,中文locationName,stationId,Date,Time,測站氣壓,溫度,相對濕度,風速,中文風向,英文風向,降水量,日照時數," +
                "當日最高溫度(°C),當日最低溫度(°C),當日平均溫度(°C)," + "\n");

            while (readxml.Read())
            {
                if (readxml.Name == "locationName" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        locationName = "";
                        locationName += (readxml.Value.Trim() + ",");
                    }
                }
                else if (readxml.Name == "stationId" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        locationName += (readxml.Value.Trim() + ",");
                    }
                }
                else if (readxml.Name == "obsTime" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        //determine rows
                        if (line)
                        {
                            //Add length
                            Value1.Add("");
                            Value2.Add("");
                            Value3.Add("");
                            Value4.Add("");
                            Value5.Add("");
                            Value6.Add("");
                            Value7.Add("");
                            Value8.Add("");
                            Value9.Add("");
                            Value10.Add("");

                            //Add Value of result
                            resultValue.Add(temp + Value1[Count] + Value2[Count] + Value3[Count] + Value4[Count] + Value5[Count] +
                                                    Value6[Count] + Value7[Count] + Value8[Count] + Value9[Count] + Value10[Count] + "\n");
                            temp = "";
                            Count += 1;
                        }

                        //Split Date and Time
                        obsTimeValue = readxml.Value.Trim().Split(' ');
                        if (obsTimeValue.Length >= 2)
                        {
                            temp += (locationName + obsTimeValue[0] + "," + obsTimeValue[1] + ",");
                            line = true;
                        }
                        else
                        {
                            temp += (locationName + obsTimeValue[0] + "," + "" + ",");
                            line = true;
                        }
                    }

                }
                if (readxml.Name == "elementName" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        if (readxml.Value == "測站氣壓")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value1[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if(Value1[Count] == "")
                        {
                            Value1[Count] = ",";
                        }
                        if (readxml.Value == "溫度")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value2[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value2[Count] == "")
                        {
                            Value2[Count] = ",";
                        }
                        if (readxml.Value == "相對濕度")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value3[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value3[Count] == "")
                        {
                            Value3[Count] = ",";
                        }
                        if (readxml.Value == "風速")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value4[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value4[Count] == "")
                        {
                            Value4[Count] = ",";
                        }
                        if (readxml.Value == "風向")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value5[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value5[Count] == "")
                        {
                            Value5[Count] = ",,";
                        }
                        if (readxml.Value == "降水量")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value6[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value6[Count] == "")
                        {
                            Value6[Count] = ",";
                        }
                        if (readxml.Value == "日照時數")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value7[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value7[Count] == "")
                        {
                            Value7[Count] = ",";
                        }
                        if (readxml.Value == "當日最高溫度(°C)")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value8[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value8[Count] == "")
                        {
                            Value8[Count] = ",";
                        }
                        if (readxml.Value == "當日最低溫度(°C)")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value9[Count] = (readxml.Value.Trim() + ",");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value9[Count] == "")
                        {
                            Value9[Count] = ",";
                        }
                        if (readxml.Value == "當日平均溫度(°C)")
                        {
                            while (readxml.Read())
                            {
                                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                                {
                                    if (readxml.Read())
                                    {
                                        Value10[Count] = (readxml.Value.Trim() + "");
                                        break;
                                    }
                                }
                            }
                        }
                        else if (Value10[Count] == "")
                        {
                            Value10[Count] = "";
                        }
                    }
                }
            }
            for (int i = 0 ; i < resultValue.Count; i++)
            {
                sw.Write(resultValue[i]);
            }

            sw.Close();
            Console.WriteLine("執行結束.");
            Console.Read();
        }
    }
}
