import java.io.File;

/**
 * It is a program to sort record in the file based on record key
 * with help limited in-memory called buffer of size 0-20 of each
 * 4096 bytes.
 * @author mark paes, vishal ms
 * @version 1.0
 */
public class HeapSort {

    // On my honor:
    // - I have not used source code obtained from another student,
    //   or any other unauthorized source, either modified or
    //   unmodified.
    //
    // - All source code and documentation used in my program is
    //   either my original work, or was derived by me from the
    //   source code published in the textbook for this course.
    //
    // - I have not discussed coding details about this project
    //   with anyone other than my partner (in the case of a joint
    //   submission), instructor, ACM/UPE tutors or the TAs assigned
    //   to this course. I understand that I may discuss the concepts
    //   of this program with other students, and that another student
    //   may help me debug my program so long as neither of us writes
    //   anything during the discussion or modifies any computer file
    //   during the discussion. I have violated neither the spirit nor
    //   letter of this restriction.


    /**
     * Entry point.
     * arg[0] input file name
     * arg[1] max buffer
     * arg[2] stats file name
     * @param args arguments to the main
     */
    public static void main(String[] args) {
        try {

            if (args.length != 3) {
                throw new IllegalArgumentException(
                    "expected argument length 3");
            }
            // file that need to be sorted
            File inputFile = new File(args[0]);
            // max buffer size
            int bufferSize = Integer.parseInt(args[1]);
            // file where stats need to be written
            String statFileName = args[2];

            // construct buffer pool with file and size
            BufferPoolLRU bufferPool = new BufferPoolLRU(inputFile, bufferSize);

            // construct the record collection to access the records
            RecordCollection recordCollection =
                new RecordCollection(bufferPool, inputFile.length());

            // initialse the sorted
            Sorter sorter = new Sorter(recordCollection);

            //call sort
            sorter.sort();

            // flush all buffer data
            bufferPool.writeAll();
            // write the stats to stats file
            bufferPool.writeStats(statFileName, inputFile);
            // print all first record of all the  blocks
            recordCollection.writeFirstRecord();
            // print the time taken for sort to happen
            sorter.writeTimeTaken(statFileName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
