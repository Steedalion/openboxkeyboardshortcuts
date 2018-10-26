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
 *
 * @author csteed
 */
public class OpenboxKeyboardShortcuts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException {

        Document doc;
        File inputXML = new File("lxqt-rc.xml"); inputXML.setReadOnly();
        doc = loadOpenboxFile(inputXML);
        doc.getDocumentElement().normalize();
        PrintWriter writer = createOutputFile("keyboard_shorcuts.rst");

        Node currentNode = doc.getDocumentElement().getElementsByTagName("keyboard").item(0).getFirstChild();
        while (currentNode != null) {
            //System.out.println("sss"+currentNode);

//            
            if ((currentNode.getNodeType() == Node.ELEMENT_NODE)) {
                Element el = (Element) currentNode;
                if (el.getTagName() == "keybind") {

                    String outputLine;
                    outputLine = el.getElementsByTagName("action").item(0).getAttributes().getNamedItem("name").getTextContent() + "\t"
                            + el.getAttribute("key")
                            + "\n";
                    writer.write(outputLine);
                } 

//                
            }else if (currentNode.getNodeType() == Node.COMMENT_NODE) {
                    Comment comment = (Comment) currentNode;

                    System.out.println("**"+comment.getTextContent()+"**");
                    writer.write("**"+comment.getTextContent()+"**"+ "\n\n");
                }
            writer.flush();
            currentNode = currentNode.getNextSibling();
        }
       writer.close();
       

    }

    public static Document loadOpenboxFile(File inputFile) {
        
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

    public static PrintWriter createOutputFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            return writer;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpenboxKeyboardShortcuts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
