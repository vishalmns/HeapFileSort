import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;

/**
 * CheckFile: Check to see if a file is sorted. This assumes that each record is
 * a pair of short ints with the first short being the key value
 *
 * @author CS Staff
 * @version 03/15/2016
 */

public class CheckFile {
    /**
     * This is an empty constructor for a CheckFile object.
     */
    public CheckFile() {
        // empty constructor
    }


    /**
     * This method checks a file to see if it is properly sorted.
     *
     * @param filename a string containing the name of the file to check
     * @return true if the file is sorted, false otherwise
     * @throws Exception either and IOException or a FileNotFoundException
     */
    public boolean checkFile(String filename) throws Exception {
        boolean isError = false;
        FileInputStream fileInputStream = new FileInputStream(filename);
        DataInputStream in =
            new DataInputStream(new BufferedInputStream(fileInputStream));

        // Prime with the first record
        short key2 = in.readShort();
        in.readShort();
        int reccnt = 0;
        try {
            while (true) {
                short key1 = key2;
                reccnt++;
                key2 = in.readShort();
                in.readShort();
                if (key1 > key2) {
                    System.out.println("key one is :" + key1);
                    System.out.println("key two is :" + key2);
                    isError = true;
                }
            }
        }
        catch (EOFException e) {

        }
        in.close();
        return !isError;
    }
}
