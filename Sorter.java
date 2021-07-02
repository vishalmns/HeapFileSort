import java.io.FileWriter;
import java.io.IOException;

public class Sorter {

    long timeTaken;
    private RecordCollection rc;


    public Sorter(RecordCollection rc) {
        this.rc = rc;
        this.timeTaken = 0L;
    }


    void sort() {
        long start = System.currentTimeMillis();
        MaxHeap maxHeap = new MaxHeap(rc, rc.getNumberOfRecords());
        maxHeap.build();
        maxHeap.arrange();
        long end = System.currentTimeMillis();
        timeTaken = end - start;
    }


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
