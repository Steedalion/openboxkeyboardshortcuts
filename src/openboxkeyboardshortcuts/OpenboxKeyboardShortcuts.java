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
import java.net.CookieHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * This program parses the Openbox configuration file to a Sphinx table.
 * 
 * @author csteed
 */
public class OpenboxKeyboardShortcuts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException {

        final String table= "\n============================= ========================\n";
        Document xmlDocument;
        File inputXML = new File("lxqt-rc.xml"); inputXML.setReadOnly();
        xmlDocument = loadOpenboxFile(inputXML);
        xmlDocument.getDocumentElement().normalize();
        PrintWriter fileWriter = createOutputFile("openbox_keyboard.rst");

        Node currentNode = xmlDocument.getDocumentElement().getElementsByTagName("keyboard").item(0).getFirstChild();
        
        while (currentNode != null) {

            if ((currentNode.getNodeType() == Node.ELEMENT_NODE)) {
                Element el = (Element) currentNode;
                if (el.getTagName() == "keybind") {

                    StringBuilder outputLine = new StringBuilder();
                    String action = el.getElementsByTagName("action").item(0).getAttributes().getNamedItem("name").getTextContent();
                    String keys = el.getAttribute("key");
                    
                    outputLine.append(action);
                    while (outputLine.length()<30) {
                        outputLine.append(" ");
                    }
                    outputLine.append(keys);
                    
                    
                    fileWriter.write("\n"+ outputLine.toString() + "\n");
                } 

//                
            }else if (currentNode.getNodeType() == Node.COMMENT_NODE) {
                    Comment comment = (Comment) currentNode;
                    String heading = table+"\n**"+removeSpaceBeforeAndAfter(comment.getTextContent())+"**\n"+table;
                    

                    System.out.println(heading);
                    fileWriter.write(heading);
                }
            fileWriter.flush();
            currentNode = currentNode.getNextSibling();
        }
       fileWriter.close();
       

    }

    private static Document loadOpenboxFile(File inputFile) {
        
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

    private static PrintWriter createOutputFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            return writer;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static String removeSpaceBeforeAndAfter(String input)
    {
        while(input.startsWith(" "))
        {
         input = input.replaceFirst(" ", "");
        }
        input = input.trim();

        return input;
    }
}
