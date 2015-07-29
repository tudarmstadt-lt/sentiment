/**
 * Created by krayush on 01-07-2015.
 */

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class XMLtoText {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("D:\\Course\\Semester VII\\Internship\\datasetConstruction\\resources\\Train_Restaurants_Contextual_Cleansed.txt.xml"));
        NodeList nodeList = document.getElementsByTagName("sentence");
        for(int x=0,size= nodeList.getLength(); x<size; x++) {
            System.out.println(size);
            System.out.println(nodeList.item(x).getAttributes().getNamedItem("lemma").getNodeValue());
        }
    }

}
