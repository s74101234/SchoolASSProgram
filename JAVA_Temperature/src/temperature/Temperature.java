/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Kuo Yu-Cheng
 */
public class Temperature {

    /**
     * @param args the command line arguments
     */
    private String readXmlPath = "./File/C-B0024-002.xml";
    private String writeCsvPath = "./File/C-B0024-002.csv";
    private String locationNameTemp,stationIdTemp;
    private String obsTimeTemp;
    private String elementNameTemp;
    private String[] obsTimeValue = new String[2];
    private int bvalue1Count = 0;
    private boolean bTitle = true;
    private boolean bfLine = false;
    private boolean blocationName = false;
    private boolean bstationId = false;
    private boolean bobsTime = false;
    private boolean belementName = false;
    private boolean bvalue = false;
    private boolean bvalue1 = false;
    private boolean bvalue11 = false;
    private boolean bvalue2 = false;
    private boolean bvalue21 = false;
    private boolean bvalue3 = false;
    private boolean bvalue31 = false;
    private boolean bvalue32 = false;
    private boolean bvalue33 = true;
    
    
   public static void main(String argv[]) throws IOException {
       Temperature tp = new Temperature();
       tp.SAXreadxml();

   }
   
   private void SAXreadxml() throws IOException
   {
       FileWriter sw = new FileWriter(writeCsvPath, false);
       try {
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();

	DefaultHandler handler = new DefaultHandler() {

	

	public void startElement(String uri, String localName,String qName,
                Attributes attributes) throws SAXException {

		//System.out.println("Start Element :" + qName);

		if (qName.equalsIgnoreCase("locationName")) {
			blocationName = true;
		}

		if (qName.equalsIgnoreCase("stationId")) {
			bstationId = true;
		}

		if (qName.equalsIgnoreCase("obsTime")) {
			bobsTime = true;
		}
                
                if (qName.equalsIgnoreCase("elementName")) {
			belementName = true;
		}
                
                if(bvalue1)
                {
                    if (qName.equalsIgnoreCase("value")) {
                        bvalue = true;
			bvalue11 = true;
                        bvalue21 = false;
                        bvalue31 = false;
                    }
                }
                if(bvalue2)
                {
                    if (qName.equalsIgnoreCase("value")) {
                        bvalue = true;
                        bvalue11 = false;
			bvalue21 = true;
                        bvalue31 = false;
                    }
                }
                if(bvalue3)
                {
                    if (qName.equalsIgnoreCase("value")) {
                        bvalue = true;
                        bvalue11 = false;
                        bvalue21 = false;
			bvalue31 = true;
                        if(bvalue33)
                        {
                            bvalue32 = true;
                        }
                    }
                }
                
//                if (qName.equalsIgnoreCase("value")) {
//			bvalue = true;                        
//		}
                            
	}

//	public void endElement(String uri, String localName,
//		String qName) throws SAXException {
//
//		//System.out.println("End Element :" + qName);
//
//	}

	public void characters(char ch[], int start, int length) throws SAXException {
            try {
                if(bTitle)
                {
                    sw.write("英文locationName,中文locationName,stationId,Date,Time," + 
                            "測站氣壓,溫度,相對濕度,風速,中文風向,英文風向,降水量,日照時數," + 
                            "當日最高溫度(°C),當日最低溫度(°C),當日平均溫度(°C)," + "\n");
                    bTitle = false;
                }
                if (blocationName) {
			locationNameTemp = (new String(ch, start, length));
			blocationName = false;
		}

		if (bstationId) {
			stationIdTemp = (new String(ch, start, length));
			bstationId = false;
		}

		if (bobsTime) {
                    
                        obsTimeTemp = (new String(ch, start, length));
                        
                        //Split Date and Time
                        obsTimeValue = obsTimeTemp.split(" ");
                        
                        if(bfLine){
                            if(bvalue1Count == 6)
                            {
                                sw.write(",,,");
                                bvalue1Count = 0;
                            }
                            else if(bvalue1Count == 7)
                            {
                                sw.write(",,"); 
                                bvalue1Count = 0;
                            }
                        bvalue33 = true;
                        sw.write("\n");
                        }
                        
                        if (obsTimeValue.length >= 2)
                        {
                            sw.write(locationNameTemp + "," + stationIdTemp + "," +
                                    obsTimeValue[0] + "," + obsTimeValue[1] + ",");
                            blocationName = false;
                        }
                        else
                        {
                            sw.write(locationNameTemp + "," + stationIdTemp + "," +
                                    obsTimeValue[0] + "," + "" + ",");
                            blocationName = false;
                        }
                    
			bobsTime = false;
                        bfLine = true;
                        
                        
		}
                
                if(belementName)
                {
                    elementNameTemp = (new String(ch, start, length)).trim();
                    
                    belementName = false;
                    if(elementNameTemp.equals("測站氣壓"))
                    {
                        bvalue1 = true;
                        bvalue2 = false;
                        bvalue3 = false;
                    }
                    if(elementNameTemp.equals("日照時數"))
                    {
                        bvalue1 = false;
                        bvalue2 = true;
                        bvalue3 = false;
                    }
                    if(elementNameTemp.equals("當日最高溫度(°C)"))
                    {
                        bvalue1 = false;
                        bvalue2 = false;
                        bvalue3 = true;
                    }
                    
                }
                if(bvalue)
                {
                    bvalue = false;
                    if (bvalue11) {
                            sw.write((new String(ch, start, length)) + ",");
                            bvalue1Count += 1 ;
                                bvalue21 = false;
                                bvalue31 = false;
                    }
                    if (bvalue21) {
                            sw.write((new String(ch, start, length)) + ",");
                            bvalue1Count += 1 ;
                                bvalue11 = false;
                                bvalue31 = false;
                    }
                    if (bvalue31) {
                        if(bvalue32)
                        {
                            sw.write(",,,,,,,,");
                            bvalue32 = false;
                            bvalue33 = false;
                        }
                            sw.write((new String(ch, start, length)) + ",");
                                bvalue11 = false;
                                bvalue21 = false;
                    }
                }
                
            } catch (IOException ex) {
                
                Logger.getLogger(Temperature.class.getName()).log(Level.SEVERE, null, ex);
                
            }
	}

     };
       saxParser.parse(readXmlPath, handler);
     } catch (Exception e) {
       e.printStackTrace();
     }
       
       sw.close();
   }

   
}
