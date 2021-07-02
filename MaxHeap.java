public class MaxHeap {

    private RecordCollection rc;
    private int length;


    public MaxHeap(RecordCollection rc, int length) {
        this.rc = rc;
        this.length = rc.getNumberOfRecords();
    }


    public void build() {
//        System.out.println("lenght is " + length);
        int startIdx = (length / 2) - 1;

        for (int i = startIdx; i >= 0; i--) {
            heapifyRecord(rc, length, i);
        }
    }


    private void heapifyRecord(RecordCollection rc, int n, int i) {

        int largest = i;
        int l = (2 * i) + 1; // left = 2*i + 1
        int r = (2 * i) + 2; // right = 2*i + 2

        if (l > n && r > n) {
            return;
        }

        Record hlarge = rc.getHeapRecord(largest);

        if (l < n) {
            Record hl = rc.getHeapRecord(l);
            if (hl.getKey() > hlarge.getKey()) {
                hlarge = hl;
                largest = l;
            }
        }

        if (r < n) {
            Record hr = rc.getHeapRecord(r);
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


    public void arrange() {

        for (int i = length - 1; i >= 0; i--) {
            rc.swap(0, i);
            heapifyRecord(rc, i, 0);
        }
    }

}
