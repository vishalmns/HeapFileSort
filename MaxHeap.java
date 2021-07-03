/**
 * To construct the max heap tree of the file to sort the file.
 * @author mark paes, vishalms
 * @version 1.0
 */
public class MaxHeap {
    /**
     * object to contains record collection
     */
    private RecordCollection collection;

    /**
     * number of records
     */
    private int length;


    /**
     * constructor to initialse the variables
     * @param collection Record collection
     * @param length numer of records
     */
    public MaxHeap(RecordCollection collection, int length) {
        this.collection = collection;
        this.length = collection.getNumberOfRecords();
    }


    /**
     * Buid the max heap tree
     */
    public void build() {
        int startIdx = (length / 2) - 1;

        for (int i = startIdx; i >= 0; i--) {
            heapifyRecord(collection, length, i);
        }
    }


    /**
     * recursive function to construct the max heap tree
     * @param rc records
     * @param n number of records
     * @param i particular record
     */
    private void heapifyRecord(RecordCollection rc, int n, int i) {

        int largest = i;
        int l = (2 * i) + 1; // left = 2*i + 1
        int r = (2 * i) + 2; // right = 2*i + 2

        // if both left and right node is more than max then return
        if (l > n && r > n) {
            return;
        }

        // fetch heap record of large
        Record hlarge = rc.getHeapRecord(largest);

        // check if left record index is less than max index
        if (l < n) {
            Record hl = rc.getHeapRecord(l);
            // left record key is greater than large record key
            if (hl.getKey() > hlarge.getKey()) {
                hlarge = hl;
                largest = l;
            }
        }

        // check if right record index is less than max index
        if (r < n) {
            Record hr = rc.getHeapRecord(r);
            // right record key is greater than large record key
            if (hr.getKey() > hlarge.getKey()) {
//                hlarge = hr;
                largest = r;
            }
        }

        // If largest is not root
        if (largest != i) {
            rc.swap(i, largest);
            // Recursively heapify the affected sub-tree
            heapifyRecord(rc, n, largest);
        }
    }


    /**
     * Arrange to obtain he max heap tree.
     */
    public void arrange() {
        // swap the largest element at the front to last.
        for (int i = length - 1; i >= 0; i--) {
            collection.swap(0, i);
            heapifyRecord(collection, i, 0);
        }
    }

}
