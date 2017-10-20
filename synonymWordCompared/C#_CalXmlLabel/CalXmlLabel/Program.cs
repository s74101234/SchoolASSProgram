using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace CalXmlLabel
{
    class Program
    {
        string idDataPath;
        string xmlPath;
        string outPutPath;

        int idDataStart = 3;
        string[] id;

        string fileName;

        string[] result;

        static void Main(string[] args)
        {
            Program Cal = new Program();

            //set IDlist
            Cal.setIdDataPath(@"../../../Files/ID.txt");
            Cal.readIdData();

            //calculation Files
            Cal.calculationAll(@"../../../Files/VOCtrainval_06-Nov-2007/Annotations/");

            //output
            Cal.setOutPutPath(@"../../../Files/result2.txt");
            Cal.outPut();

            Console.Write("complete");
            Console.Read();
        }

        void outPut()
        {
            StreamWriter sw = new StreamWriter(outPutPath, false);

            sw.Write("FileName,");
            for (int i = 0; i < id.Length; i++)
            {
                if (i == (id.Length - 1))
                {
                    sw.Write(id[i].Trim() + "\n");
                }
                else
                {
                    sw.Write(id[i].Trim() + ",");
                }
            }

            for (int i = 0; i < result.Length; i++)
            {
                sw.Write(result[i] + "\n");
            }

            sw.Close();

        }

        void calculationAll(string fileXmlPath)
        {
            String[] FileCollection;
            FileCollection = Directory.GetFiles(fileXmlPath, "*.xml");
            result = new string[FileCollection.Length];
            
            for (int i = 0; i < FileCollection.Length; i++)
            {
                setXmlPath(FileCollection[i]);
                result[i] = compare(readXmlData());
            }

            //for (int i = 0; i < result.Length; i++)
            //{
            //    Console.WriteLine(result[i]);
            //}
        }

        string compare(string xmlData)
        {
            string[] sXmlData = xmlData.Split(',');
            string xmlResult = fileName + ",";
            Boolean isSuccess;

            for (int i = 0; i < id.Length; i++)
            {

                isSuccess = false;

                for (int j = 0; j < sXmlData.Length; j++)
                {
                    if (id[i].Trim().Equals(sXmlData[j].Trim()))
                    {
                        isSuccess = true;
                    }
                }

                if (i == (id.Length - 1))
                {
                    if (isSuccess)
                    {
                        xmlResult += "1";
                    }
                    else
                    {
                        xmlResult += "0";
                    }
                }
                else
                {
                    if (isSuccess)
                    {
                        xmlResult += "1,";
                    }
                    else
                    {
                        xmlResult += "0,";
                    }
                }
                
            }
            return xmlResult;
        }

        void readIdData()
        {
            //txt轉成陣列
            string TxtAll = File.ReadAllText(idDataPath);
            string[] STxt = TxtAll.Split('\t', '\n');

            id = new string[((STxt.Length-2) / 2)];

            for (int i = 0; i < id.Length; i++)
            {
                id[i] = STxt[idDataStart];
                idDataStart += 2;
                //Console.WriteLine(id[i]);

            }

        }
        string readXmlData()
        {
            string xmlData = "";

            XmlTextReader readXml = new XmlTextReader(xmlPath);
            Boolean inObject = false;

            while (readXml.Read())
            {
                if (readXml.Name == "filename" && readXml.NodeType == XmlNodeType.Element)
                {
                    readXml.Read();
                    fileName = readXml.Value;
                }

                if (readXml.Name == "object" && readXml.NodeType == XmlNodeType.Element)
                {
                    inObject = true;
                }

                if (readXml.Name == "name" && inObject && readXml.NodeType == XmlNodeType.Element)
                {
                    readXml.Read();
                    xmlData += readXml.Value + ",";

                    inObject = false;
                }
            }

            return xmlData;
        }

        void setIdDataPath(string idDataPath)
        {
            this.idDataPath = idDataPath;
        }

        void setXmlPath(string xmlPath)
        {
            this.xmlPath = xmlPath;
        }

        void setOutPutPath(string outPutPath)
        {
            this.outPutPath = outPutPath;
        }

    }
}
