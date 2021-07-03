import java.io.FileWriter;
import java.io.IOException;

/**
 * Build the max heap and then sort the record.
 * @author mark paes, vishalms
 * @version 1.0
 */
public class Sorter {
    /**
     * Time taken to sort the file
     */
    private long timeTaken;
    /**
     * Records that need to be sorted
     */
    private RecordCollection rc;


    /**
     * initlise the objects
     * @param rc object that contains records
     */
    public Sorter(RecordCollection rc) {
        this.rc = rc;
        this.timeTaken = 0L;
    }


    /**
     * sort all the records
     */
    void sort() {
        long start = System.currentTimeMillis();
        MaxHeap maxHeap = new MaxHeap(rc, rc.getNumberOfRecords());
        // build max heap tree
        maxHeap.build();
        // sort the tree
        maxHeap.arrange();
        long end = System.currentTimeMillis();
        // time taken for the heap sort to execute
        timeTaken = end - start;
    }


    /**
     * Time taken to sort the records
     * max heap + arrange
     * @param statFileName write the time taken to stat file
     */
    public void writeTimeTaken(String statFileName) {
        try {
            FileWriter fw = new FileWriter(statFileName, true);
            fw.write("Time to Sort: " + timeTaken + "\n");
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
