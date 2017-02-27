package Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  Test to check if the original world file was compressed accurately to the
 *  compressed version of the file.
 *The original image TIF file was around 300mb but using Java AdvancedIO (JAI) library accessing
 *  the file continuously and in different regions would have been very inefficient.
 */

public class AccuracyTest {

    public static final String filePrefixComp = "./src/world/x";
    public static final String filePrefixOrig = "./src/wTest/x";

/**  Coordinates of long lat for each corner in the file.
 *   Upper Left  (-180.0013889,  90.0013889)
 *   Lower Left  (-180.0013889, -64.9986111)  ORIGIN
 *   Upper Right ( 179.9986111,  90.0013889)
 *   Lower Right ( 179.9986111, -64.9986111)
 *   Center      (  -0.0013889,  12.5013889)
 */

    public static final double minLat = -64.998611;  //= file = y value
    public static final double maxLat = 90.0013889;
    public static final double minLon = -180.0013889;//= line = x value
    public static final double maxLon = 179.9986111;//= line = x value
    public static final double delta = 0.002777777778;//difference between each lat long.

    public static final double latDiff = (maxLat-minLat);
    public static final double lonDiff = (maxLon-minLon);
    public static final int width = 129600;
    public static final int height = 55800;


    /**
     * Find out the number of digits in the integer i.
     */
    public static int digits(int i) {
        int count = 0;

        while (10*i/10 != 0) {
            count++;
            i/=10;
        }
        return count;
    }

    /**
     *  Method to convert the integer into the relevant 5 digit file ID. For use in looping through each
     *  55000 file.
     */
    public static String intToID(int i, int offset) {
        StringBuilder sb = new StringBuilder();
        i+=offset;
        int digits = digits(i);

        if (digits==0) {
            sb.append("00000");
        } else if (digits == 1) {
            sb.append("0000");
            sb.append(i);
        } else if (digits == 2) {
            sb.append("000");
            sb.append(i);
        } else if (digits == 3) {
            sb.append("00");
            sb.append(i);
        } else if (digits == 4) {
            sb.append("0");
            sb.append(i);
        } else {
            sb.append(Integer.toString(i));
        }
        return sb.toString();
    }

    /**
     * Methods to convert each latitude and longitude points to the actual file and
     * the index into that file.
     */
    public static int longToIndexX(double lon) {
        return (int) Math.round((lon - minLon) / delta);
    }
    public static int latToLineY(double lat) {
        return (int) Math.round((lat - minLat) / delta);
    }

    /**
     * Method to convert the long original code to the actual compressed code
     */
    public static String convert(String code) {
        String ret="";

        switch (code) {
            case "11":
                ret = "e";
                break;
            case "14":
                ret = "f";
                break;
            case "20":
                ret = "g";
                break;
            case "30":
                ret = "h";
                break;
            case "40":
                ret = "i";
                break;
            case "50":
                ret = "j";
                break;
            case "60":
                ret = "k";
                break;
            case "70":
                ret = "l";
                break;
            case "90":
                ret = "m";
                break;
            case "\\s":
                ret = "";
                break;
            case "210":
                ret = "0";
                break;
            case "200":
                ret = "1";
                break;
            case "220":
                ret = "2";
                break;
            case "230":
                ret = "3";
                break;
            case "100":
                ret = "a";
                break;
            case "110":
                ret = "b";
                break;
            case "120":
                ret = "c";
                break;
            case "130":
                ret = "d";
                break;
            case "140":
                ret = "4";
                break;
            case "150":
                ret = "5";
                break;
            case "160":
                ret = "6";
                break;
            case "170":
                ret = "7";
                break;
            case "180":
                ret = "8";
                break;
            case "190":
                ret = "9";
                break;
            case " ":
                ret ="";
                break;
            default:
        }
        return ret;
    }


    /**
     *  Method to test if each point in the world data is correctly mapped and compressed using the agreed
     *  coding scheme.
     *  Loops through each file and the line in that file and checks if each point correctly mapped.
     *
     * @param compFile Compressed file name
     * @param origFile original file name
     * @param origLine line in the original world file
     * @param compLine line in the compressed world file
     * @param compCoded original code for the compressed file value
     * @param comp value of the index in the compressed file
     * @param cR buffered reader for compressed files
     * @param oR buffered reader for original files
     * @throws IOException
     * @throws IncorrectCompression
     */
    public static void testCompression(String compFile,String origFile,
                                       String origLine, String compLine,
                                       String compCoded, String comp,
                                       BufferedReader cR, BufferedReader oR) throws IOException, IncorrectCompression {

        for (int y = 0; y<height; y++) {

            compFile = filePrefixComp + intToID(y,0);
            origFile = filePrefixOrig + intToID(y,5);

            cR = new BufferedReader(new InputStreamReader(new FileInputStream(compFile)));
            oR = new BufferedReader(new InputStreamReader(new FileInputStream(origFile)));

            origLine = cR.readLine();
            String[] str = origLine.substring(1).split("\\s");

            compLine = oR.readLine();
            for (int c = 1; c < str.length-1; c++) {
                compCoded = convert(str[c]);
                comp = "" + compLine.charAt(c);
                if (!(compCoded).equals(comp)) {

                    throw new IncorrectCompression(y,c,comp,compCoded,compFile,origFile);
                }
            }
            System.out.println(y);
            cR.close();
            cR.close();
        }
    }
    public static void main(String[] args) throws IOException, LineNotCorrectSizeException {
        //Prevent initialisations for each file for faster testing.
        //Currently takes 7-8 minutes.
        String compFile="",origFile="",origLine="",compLine="",compCoded="",comp="";
        BufferedReader cR=null,oR=null;

        try {
            testCompression(compFile,origFile,origLine,compLine,compCoded,comp,cR,oR);
        } catch (IncorrectCompression incorrectCompression) {
            incorrectCompression.printStackTrace();
            System.out.println("TEST FAILED");
        }

        System.out.println("TEST PASSED");
    }

}
