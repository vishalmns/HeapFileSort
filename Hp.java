import java.util.List;

public class Hp {

    private RecordCollection rc;


    public Hp(RecordCollection rc) {
        this.rc = rc;
    }


    void sort() {

        MaxHeap maxHeap = new MaxHeap(rc, rc.getLength());

        maxHeap.build();

        maxHeap.arrange();

    }
}
