public class MaxHeap {

    private RecordCollection rc;
    private int length;


    public MaxHeap(RecordCollection rc, int length) {
        this.rc = rc;
        this.length = rc.getLength();
    }


    public void build() {
        int startIdx = (length / 2) - 1;

        for (int i = startIdx; i >= 0; i--) {
            heapifyRecord(rc, length, i);
        }
    }


    private void heapifyRecord(RecordCollection rc, int n, int i) {

        int largest = i;
        int l = (2 * i) + 1; // left = 2*i + 1
        int r = (2 * i) + 2; // right = 2*i + 2

        Record hlarge = rc.getHeapRecord(largest);
        Record hl = rc.getHeapRecord(l);
        Record hr = rc.getHeapRecord(r);


        // If left child is larger than root
        if (l < n && hl.getKey() > hlarge.getKey()) {
            largest = l;
//            hlarge = rc.getHeapRecord(largest);
            hlarge = hl;
        }
        // If right child is larger than largest so far
        if (r < n && hr.getKey() > hlarge.getKey()) {
            largest = r;
//            hlarge = rc.getHeapRecord(largest);
            hlarge = hr;
        }
        // If largest is not root
        if (largest != i) {
            rc.swap(i, largest);
            // Recursively heapify the affected sub-tree
            heapifyRecord(rc, n, largest);
        }
    }


    public void arrange() {

        for (int i = length - 1; i >=0 ; i--) {
            rc.swap(0, i);
            heapifyRecord(rc,i,0);
        }
    }

}
