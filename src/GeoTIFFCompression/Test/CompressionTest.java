package GeoTIFFCompression.Test;

import java.io.*;

/**
 * Created by miteyan on 06/02/2017.
 *
 * Original test file for older iterations of the compression file.
 *
 */

public class CompressionTest {
    private static final String origF = "./src/output.asc";
    private static final String compF = "./src/output2.txt";

    public static void skipHeader(BufferedReader br) throws IOException {
        for (int i =0 ;i<6; i++) {
            br.readLine();
        }
    }

    public static String convertToOrig(String comp) {
        String[][] conversions = {
                {"11","14","20","30","40","50","60","70","90","210","200","220","230","100","110","120","130","140","150","160","170","180","190"},
                {"e","f","g","h","i","j","k","l","m","0","1","2","3","a","b","c","d","4","5","6","7","8","9"}};
        StringBuilder sb = new StringBuilder();
//        System.out.println(comp);
        for (char c: comp.toCharArray()) {
            int i = 0;
//            System.out.println(c);
            while (!conversions[1][i].equals(c+"")) {
                i++;
            }
            sb.append(conversions[0][i]);
        }
//        System.out.println(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader origFile = new BufferedReader(new InputStreamReader(new FileInputStream(origF)));
        BufferedReader compFile = new BufferedReader(new InputStreamReader(new FileInputStream(compF)));

        String origL="";
        String compL="";
        skipHeader(origFile);
        int i =0;
        while ( (compL = compFile.readLine()) != null ) {

            origL = origL.replaceAll("\\s","");
            String compp = convertToOrig(compL);
            if (!(compp.equals(origL))) {
                System.out.println("Failure at line: " + i);
            }
//            System.out.println(i);
//            i++;
        }
        System.out.println("TEST PASSED");
        origFile.close();
        compFile.close();

    }
}