/**
 * Created by miteyan on 23/02/2017.
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FlickrURLs {

    private static final String APIKEY = "906e25f0690cfbd53c7ddec7fede56f1";
    private static final String APISIG = "7d620a1ce0bc4cba40a8fba27b6804b6";

//String testURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=906e25f0690cfbd53c7ddec7fede56f1&text=Coldhams+common&geo_context=&lat=52.205614&lon=0.150837radius=1&format=json&nojsoncallback=1&api_sig=1d50c2ed1d5959951679c461ede305b2";

    //get flickr xml url
    public static String getURL(String name, double lat, double lon, double radius) {
        return "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                APIKEY+ "&text=" + name + "&geo_context=&lat=" + lat + "&lon=" + lon + "&radius=" +
                radius + "&format=rest&api_sig="+APISIG;
    }

    public static String getImageURL(String farm, String server, String id, String secret) {
        return "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
    }

    public static List<String> XMLParse(String flickrURL) {
        List<String> imageURLs = new ArrayList<>();
        String farm,id,secret,server;
        try {
            URL url = new URL(flickrURL);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(url.openStream());
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("photo");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    farm = eElement.getAttribute("farm");
                    id = eElement.getAttribute("id");
                    secret = eElement.getAttribute("secret");
                    server = eElement.getAttribute("server");

                    imageURLs.add(getImageURL(farm,server,id,secret));
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("URL ");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageURLs;
    }


    public static List<String> getImageURLs (String name, double lat, double lon, double radius) {
        return XMLParse(getURL(name,lat,lon,radius));
    }

    public static void main(String[] args) {
        //Inputs
        String name = "Coldhams+common";
        double lat = 52.205614;
        double lon = 0.150837;
        double radius = 0.5;

        List<String> urls = getImageURLs(name,lat,lon,radius);
        for (String u: urls) {
            System.out.println(u);
        }

    }
}
