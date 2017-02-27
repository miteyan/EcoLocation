package Test;

/**
 * Exception thrown if the file line size is not equal to 129600 or the number of width pixels.
 */
public class LineNotCorrectSizeException extends Exception {

    public LineNotCorrectSizeException(int lineNum, int size) {
        System.out.println("Line number: " + lineNum+ " has length: " + size);
    }

}
