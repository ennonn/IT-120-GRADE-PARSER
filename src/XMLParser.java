import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {
    public static Document parseXMLFile(String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            return docBuilder.parse(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getChildValue(Element parentElement, String childTagName) {
        NodeList nodeList = parentElement.getElementsByTagName(childTagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
