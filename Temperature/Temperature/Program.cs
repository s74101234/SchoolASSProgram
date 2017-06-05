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

        static void Main(string[] args)
        {
            //創建一個新的txt(true = 不覆蓋 && false = 覆蓋)
            StreamWriter sw = new StreamWriter(NewTxtPath, false);

            XmlTextReader readxml = new XmlTextReader(XmlPath);
            string[] obsTimeValue = new string[2];
            string locationName = "";
            Boolean line = false;

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
                if (readxml.Name == "stationId" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        locationName += (readxml.Value.Trim() + ",");
                    }
                }
                if (readxml.Name == "obsTime" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        if (line)
                        {
                            sw.Write("\n");
                        }
                        obsTimeValue = readxml.Value.Trim().Split(' ');
                        if (obsTimeValue.Length >= 2)
                        {
                            sw.Write(locationName + obsTimeValue[0] + "," + obsTimeValue[1] + ",");
                            line = true;
                        }
                        else
                        {
                            sw.Write(locationName + obsTimeValue[0] + "," + " " + ",");
                            line = true;
                        }
                        
                    }
                }
                if (readxml.Name == "value" && readxml.NodeType == XmlNodeType.Element)
                {
                    if (readxml.Read())
                    {
                        sw.Write(readxml.Value.Trim() + ",");
                    }
                    
                }
            }

            sw.Close();
            Console.Write("End");
            Console.Read();
        }
    }
}
