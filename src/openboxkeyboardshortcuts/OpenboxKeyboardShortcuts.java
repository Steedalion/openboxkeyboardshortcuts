/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openboxkeyboardshortcuts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *
 * @author csteed
 */
public class OpenboxKeyboardShortcuts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException {
        
        Document doc;
        doc = loadOpenboxFile("lxqt-rc.xml");
        doc.getDocumentElement().normalize();
        NodeList keybindList = doc.getElementsByTagName("keybind");
        for (int i = 0; i < keybindList.getLength(); i++) {
            Node keybind =keybindList.item(i);
             if (keybind.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) keybind;
            String outputLine;
            outputLine = el.getElementsByTagName("action").item(0).getAttributes().getNamedItem("name").getTextContent()+"\t\t"
                    +el.getAttribute("key")
                    +"\t";
            System.out.println(outputLine);
        }
            
        }
        
        
       
         
    }
    
    public static Document loadOpenboxFile(String fileName) 
    {
        File inputFile = new File(fileName);
        inputFile.setReadOnly();
        try {
            DocumentBuilderFactory dfact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dfact.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            
            return doc;
        } catch (SAXException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    public   static PrintWriter createOutputFile(String fileName)
    {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            return writer;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
