/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperature;

import java.io.File;
import java.io.FileWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Kuo Yu-Cheng
 */
public class Temperature {

    /**
     * @param args the command line arguments
     */
    private String readXmlPath = "./File/C-B0024-003.xml";
    private String writeCsvPath = "./File/C-B0024-003.csv";
    private String locationNameTemp ;
    private String stationIdTemp ;
    private String[] obsTimeTemp = new String[2];
    private void readxml()
    {
    try {
        
        FileWriter sw = new FileWriter(writeCsvPath, false);

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(readXmlPath));

               NodeList locationList = doc.getElementsByTagName("location");   
               for (int locationtemp = 0; locationtemp < locationList.getLength(); locationtemp++) 
               {
                   Node locationNode = locationList.item(locationtemp);
                   if (locationNode.getNodeType() == Node.ELEMENT_NODE) 
                   {
                       Element locationeElement = (Element) locationNode;
                       locationNameTemp = locationeElement.getElementsByTagName("locationName").item(0).getTextContent();
                       stationIdTemp = locationeElement.getElementsByTagName("stationId").item(0).getTextContent();
                       
                            NodeList nList = doc.getElementsByTagName("time");
                            for (int temp = 0; temp < nList.getLength(); temp++) {

                                    Node nNode = nList.item(temp);

                                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                                            Element eElement = (Element) nNode;
                                            
                                            obsTimeTemp = eElement.getElementsByTagName("obsTime").item(0).getTextContent().split(" ");

                                            if (obsTimeTemp.length >= 2)
                                            {
                                                sw.write(locationNameTemp + "," + stationIdTemp + ","
                                                    + obsTimeTemp[0] + "," + obsTimeTemp[1] + ",");
                                            }
                                            else
                                            {
                                                sw.write(locationNameTemp + "," + stationIdTemp + ","
                                                    + obsTimeTemp[0] + "," + "" + ",");
                                            }
                                            

                                            if(eElement.getElementsByTagName("elementName").item(0).getTextContent().equals("測站氣壓") )
                                            {
                                                if (eElement.getElementsByTagName("elementName").item(0).getTextContent().equals("日照時數")) 
                                                {
                                                    sw.write( eElement.getElementsByTagName("value").item(0).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(1).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(2).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(3).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(4).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(5).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(6).getTextContent() + "," );
                                                    sw.write("," + "," + "\n");
                                                    
//                                                    System.out.println("測站氣壓 : " + eElement.getElementsByTagName("value").item(0).getTextContent());
//                                                    System.out.println("溫度 : " + eElement.getElementsByTagName("value").item(1).getTextContent());
//                                                    System.out.println("相對濕度 : " + eElement.getElementsByTagName("value").item(2).getTextContent());
//                                                    System.out.println("風速 : " + eElement.getElementsByTagName("value").item(3).getTextContent());
//                                                    System.out.println("中文風向,英文風向 : " + eElement.getElementsByTagName("value").item(4).getTextContent());
//                                                    System.out.println("降水量 : " + eElement.getElementsByTagName("value").item(5).getTextContent());
//                                                    System.out.println("日照時數 : " + eElement.getElementsByTagName("value").item(6).getTextContent());
                                                }else
                                                {
                                                    sw.write( eElement.getElementsByTagName("value").item(0).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(1).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(2).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(3).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(4).getTextContent() + "," );
                                                    sw.write( eElement.getElementsByTagName("value").item(5).getTextContent() + "," );
                                                    sw.write("," );
                                                    sw.write("," + "," + "\n");
                                                    
//                                                System.out.println("測站氣壓 : " + eElement.getElementsByTagName("value").item(0).getTextContent());
//                                                System.out.println("溫度 : " + eElement.getElementsByTagName("value").item(1).getTextContent());
//                                                System.out.println("相對濕度 : " + eElement.getElementsByTagName("value").item(2).getTextContent());
//                                                System.out.println("風速 : " + eElement.getElementsByTagName("value").item(3).getTextContent());
//                                                System.out.println("中文風向,英文風向 : " + eElement.getElementsByTagName("value").item(4).getTextContent());
//                                                System.out.println("降水量 : " + eElement.getElementsByTagName("value").item(5).getTextContent());
//                                                System.out.println("日照時數 : " + "");
                                                }
                                                
                                            }else if(eElement.getElementsByTagName("elementName").item(0).getTextContent().equals("當日最高溫度(°C)"))
                                            {
                                                sw.write("," + "," + "," + "," + "," + "," + ",");
                                                sw.write( eElement.getElementsByTagName("value").item(0).getTextContent() + "," );
                                                sw.write( eElement.getElementsByTagName("value").item(1).getTextContent() + "," );
                                                sw.write( eElement.getElementsByTagName("value").item(2).getTextContent()  + "\n");
                                                
//                                                System.out.println("當日最高溫度(°C) : " + eElement.getElementsByTagName("value").item(0).getTextContent());
//                                                System.out.println("當日最低溫度(°C) : " + eElement.getElementsByTagName("value").item(1).getTextContent());
//                                                System.out.println("當日平均溫度(°C) : " + eElement.getElementsByTagName("value").item(2).getTextContent());
                                            }
                                    }
                            }
                   }
               }
               
        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
        //System.exit (0);

    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Temperature temperature = new Temperature();
        temperature.readxml();
    }
    
}
