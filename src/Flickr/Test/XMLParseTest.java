package Flickr.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by miteyan on 23/02/2017.
 */


public class XMLParseTest {
    public static void main(String argv[]) throws MalformedURLException {

        try {
            URL url = new URL("https://api.flickr.com/services" +
                    "/rest/?method=flickr.photos.search&api_key=" +
                    "906e25f0690cfbd53c7ddec7fede56f1&text=Coldhams+common&geo_context=" +
                    "&lat=52.205614&lon=0.150837&radius" +
                    "=0.5&format=rest&api_sig=7d620a1ce0bc4cba40a8fba27b6804b6");
//            File fXmlFile = new File(url.getFile());
//            File fXmlFile = new File("./src/myOutput.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("photo");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("farm : " + eElement.getAttribute("farm"));
                    System.out.println("id : " + eElement.getAttribute("id"));
                    System.out.println("isfamily : " + eElement.getAttribute("isfamily"));
                    System.out.println("isfriend : " + eElement.getAttribute("isfriend"));
                    System.out.println("ispublic : " + eElement.getAttribute("ispublic"));
                    System.out.println("owner : " + eElement.getAttribute("owner"));
                    System.out.println("secret : " + eElement.getAttribute("secret"));
                    System.out.println("server : " + eElement.getAttribute("server"));
                    System.out.println("title : " + eElement.getAttribute("title"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
