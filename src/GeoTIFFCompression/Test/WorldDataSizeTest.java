package GeoTIFFCompression.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorldDataSizeTest {

    /**
     * Original compression test for the larger 30gb files.
     * Checking to see if the correct file size was created and that each pixel mapped and no class
     * of terrain missed out.
     * The original compression coding system:
     *
    //Compressed | original | description
    //    11	                    Post-flooding or irrigated croplands (or aquatic)
    //    14                       	Rainfed croplands
    //    20                    	Mosaic cropland (50-70%) / vegetation (grassland/shrubland/forest) (20-50%)
    //    30                    	Mosaic vegetation (grassland/shrubland/forest) (50-70%) / cropland (20-50%)
    //    40                    	Closed to open (>15%) broadleaved evergreen or semi-deciduous forest (>5m)
    //    50	                    Closed (>40%) broadleaved deciduous forest (>5m)
    //    60	                    Open (15-40%) broadleaved deciduous forest/woodland (>5m)
    //    70	                    Closed (>40%) needleleaved evergreen forest (>5m)
    //    90	                    Open (15-40%) needleleaved deciduous or evergreen forest (>5m)
    //
    //    10             100        Closed to open (>15%) mixed broadleaved and needleleaved forest (>5m)
    //    15             110     	Mosaic forest or shrubland (50-70%) / grassland (20-50%)
    //    12             120	    Mosaic grassland (50-70%) / forest or shrubland (20-50%)
    //    13             130	    Closed to open (>15%) (broadleaved or needleleaved, evergreen or deciduous) shrubland (<5m)
    //    4              140	    Closed to open (>15%) herbaceous vegetation (grassland, savannas or lichens/mosses)
    //    5              150	    Sparse (<15%) vegetation
    //    6              160	    Closed to open (>15%) broadleaved forest regularly flooded (semi-permanently or temporarily) - Fresh or brackish water
    //    7              170	    Closed (>40%) broadleaved forest or shrubland permanently flooded - Saline or brackish water
    //    8              180	    Closed to open (>15%) grassland or woody vegetation on regularly flooded or waterlogged soil - Fresh, brackish or saline water
    //    9              190	    Artificial surfaces and associated areas (Urban areas >50%)
    //    1              200	    Bare areas
    //    0              210	    Water bodies
    //    2              220	    Permanent snow and ice
    //    3              230	    No data (burnt areas, clouds,…)
    */


//    public static String getArray(String[] codes) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < codes.length; i++) {
//            String orig = (codes[i]);
//            String comp = convertToOrig(orig);
//            sb.append(comp);
//        }
//        return sb.toString();
//    }

    public static final String comp = "./src/output2.txt";
    public static final int width = 129600;

    public static void main(String[] args) throws IOException, LineNotCorrectSizeException {
        BufferedReader compFile = new BufferedReader(new InputStreamReader(new FileInputStream(comp)));

        String compL;
        int i=0,lineSize;
        while ((compL = compFile.readLine()) != null ) {
            lineSize = compL.length();
            if(lineSize != width){
                System.out.println("Line number: " + i+ " has length: " + lineSize);
                if (i<55799) {
                    throw new LineNotCorrectSizeException(i,lineSize);
                }
            }
            i++;
            System.out.println(i);
        }
        System.out.println("TEST PASSED");
        compFile.close();

    }
}