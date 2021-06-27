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
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        Record hlarge = rc.getHeapRecord(i);
        Record hl = rc.getHeapRecord(l);
        Record hr = rc.getHeapRecord(r);


        // If left child is larger than root
        if (l < n && hl.getKey() > hlarge.getKey())
            largest = l;

        // If right child is larger than largest so far
        if (r < n && hr.getKey() > hlarge.getKey())
            largest = r;

        // If largest is not root
        if (largest != i) {
//            int swap = arr[i];
//            arr[i] = arr[largest];
//            arr[largest] = swap;

            rc.swap(i, largest);


            // Recursively heapify the affected sub-tree
            heapifyRecord(rc, n, largest);
        }
    }


    public void arrange() {

        for (int i = rc.getLength() - 1; i >0 ; i--) {
            rc.swap(0, i);
            heapifyRecord(rc,i,0);
        }
    }


    //    private void heapify(RecordCollection rc, int n, int i) {
//
//        int largest = i; // Initialize largest as root
//        int l = 2 * i + 1; // left = 2*i + 1
//        int r = 2 * i + 2; // right = 2*i + 2
////
////        // If left child is larger than root
////        if (l < n && arr[l] > arr[largest])
////            largest = l;
////
////        // If right child is larger than largest so far
////        if (r < n && arr[r] > arr[largest])
////            largest = r;
////
////        // If largest is not root
////        if (largest != i) {
////            int swap = arr[i];
////            arr[i] = arr[largest];
////            arr[largest] = swap;
////
////            // Recursively heapify the affected sub-tree
////            heapify(arr, n, largest);
////        }
//
//        // If left child is larger than root
//        if (l < n && rc.getValue(l) > rc.getValue(largest))
//            largest = l;
//
//        // If right child is larger than largest so far
//        if (r < n && rc.getValue(r) > rc.getValue(largest))
//            largest = r;
//
//        // If largest is not root
//        if (largest != i) {
////            int swap = arr[i];
////            arr[i] = arr[largest];
////            arr[largest] = swap;
//
//            rc.swap(i, largest);
//
//            // Recursively heapify the affected sub-tree
//            heapifyRecord(rc, n, largest);
//        }
//    }

}
