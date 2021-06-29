import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RecordCollection {

    private BufferPool pool;

    private int length;

    private int recordSize = 4;

    public int getLength() {
    return this.length;
    }

    public RecordCollection(BufferPool pool, long length) {
        this.pool = pool;
        this.length = (int)(length / recordSize);
    }

    public void swap(int a, int b) {
        Record heapRecordA = this.getHeapRecord(a);
        Record heapRecordB = this.getHeapRecord(b);
        this.setHeapRecord(heapRecordA, b);
        this.setHeapRecord(heapRecordB, a);
    }


    private void setHeapRecord(Record record, int recordNumber) {
        int index = recordNumber * recordSize;

        byte[] bytes = buildBytes(record);
        pool.setRecordBytes(bytes, index);

    }


    private byte[] buildBytes(Record record) {

        short[] shorts = new short[] { record.getKey(), record.getValue() };

        byte[] resultBytes = new byte[shorts.length * 2];
        ByteBuffer.wrap(resultBytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer().put(shorts);

        return resultBytes;

    }


    public Record getHeapRecord(int recordNumber) {

        int index = recordNumber * recordSize;

        byte[] recordBytes = pool.getRecordBytes(index, recordSize);

        Record record = buildRecord(recordBytes);

        return record;
    }

    private Record buildRecord(byte[] recordBytes) {
        short[] shorts = new short[recordBytes.length / 2];

        ByteBuffer.wrap(recordBytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer()
            .get(shorts);

        if (shorts.length != 2) {
            System.out.println("bad length");
        }

        return new Record(shorts[0], shorts[1]);

    }

}
