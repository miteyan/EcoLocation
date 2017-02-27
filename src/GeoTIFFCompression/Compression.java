package GeoTIFFCompression;

/**
 * Created by miteyan on 27/02/2017.
 */
import java.io.*;

/**
 * Created by miteyan on 05/02/2017.
 */
public class Compression {
    /**
     * Final compression
     Compressed | original | description
     e              11       Post-flooding or irrigated croplands (or aquatic)
     f              14       Rainfed croplands
     g              20       Mosaic cropland (50-70%) / vegetation (grassland/shrubland/forest) (20-50%)
     h              30       Mosaic vegetation (grassland/shrubland/forest) (50-70%) / cropland (20-50%)
     i              40       Closed to open (>15%) broadleaved evergreen or semi-deciduous forest (>5m)
     j              50	    Closed (>40%) broadleaved deciduous forest (>5m)
     k              60	    Open (15-40%) broadleaved deciduous forest/woodland (>5m)
     l              70	    Closed (>40%) needleleaved evergreen forest (>5m)
     m              90	    Open (15-40%) needleleaved deciduous or evergreen forest (>5m)
     a              100      Closed to open (>15%) mixed broadleaved and needleleaved forest (>5m)
     b              110     	Mosaic forest or shrubland (50-70%) / grassland (20-50%)
     c              120	    Mosaic grassland (50-70%) / forest or shrubland (20-50%)
     d              130	    Closed to open (>15%) (broadleaved or needleleaved, evergreen or deciduous) shrubland (<5m)
     4              140	    Closed to open (>15%) herbaceous vegetation (grassland, savannas or lichens/mosses)
     5              150	    Sparse (<15%) vegetation
     6              160	    Closed to open (>15%) broadleaved forest regularly flooded (semi-permanently or temporarily) - Fresh or brackish water
     7              170	    Closed (>40%) broadleaved forest or shrubland permanently flooded - Saline or brackish water
     8              180	    Closed to open (>15%) grassland or woody vegetation on regularly flooded or waterlogged soil - Fresh, brackish or saline water
     9              190	    Artificial surfaces and associated areas (Urban areas >50%)
     1              200	    Bare areas
     0              210	    Water bodies
     2              220	    Permanent snow and ice
     3              230	    No data (burnt areas, clouds,…)
     */
    private static int rows = 55800;
    /**
     * Convert original 3 digit Greyscale code value into a 1 digit code.
     * @param code Original code
     * @return Compressed code
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
            case "200":
                ret = "1";
                break;
            case "210":
                ret = "0";
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
                ret="";
        }
        return ret;
    }

    /**
     * Convert an array of original codes into a string of compressed codes eliminating all of the
     * spaces and reducing file size by 3-4 times.
     * @param codes Array of original codes
     * @return String of compressed codes of single char.
     */
    public static String getCompressed(String[] codes) {
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            if (!code.equals(" ")) {
                String comp = convert(code);
                sb.append(comp);
            }
        }
        return sb.toString();
    }

    //read top 6 header lines

    /**
     * Skip the top 6 metadata file information lines to seek to where the data belongs.
     * @param br Buffered reader for the original file.
     * @throws IOException if not properly accessed.
     */
    public static void skipHeader(BufferedReader br) throws IOException {
        for (int i =0 ;i<6; i++) {
            br.readLine();
        }
    }

    /**
     * Method to compress the reading in the large file and writing it into a new smaller file which is
     * easier to access.
     * @param br Reader to get input from the uncompressed file
     * @param bw Writer to write compressed strings into the new output file. From the original 30gb asc file
     *           to the txt file of smaller file size.
     * @throws IOException
     */
    public static void compress (BufferedReader br, BufferedWriter bw) throws IOException {
        String line;
        int i =0;
        while ( (line = br.readLine())!=null) {
            i++;
            String[] strings = line.split("\\s");
            String comp = getCompressed(strings);
            bw.write(comp);
            System.out.println(i);
            bw.newLine();
        }
    }
    public static void main(String[] args) throws IOException {
        String input = "./src/output.asc";
        String outfile = "./src/output3.txt";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        FileWriter fw = new FileWriter(outfile);
        BufferedWriter bw = new BufferedWriter(fw);
        skipHeader(br);
        try {
            compress(br, bw);
        } catch (IOException e) {
            System.err.println("Compression failed");
        }
        br.close();
    }
}