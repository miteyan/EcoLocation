package Test;

/**
 * Exception to print out relevent information for an incorrect compression
 * at a particular file and index in that file.
 */

public class IncorrectCompression extends Exception {
    public IncorrectCompression(int y, int c, String comp, String compCoded,String compFile, String origFile) {
        System.out.println(compFile);
        System.out.println(origFile);
        System.out.println("ERROR LINE " + y+ " "+ c);
        System.out.println("Compressed : " + comp + " Original: "+ compCoded +".");
    }
}
