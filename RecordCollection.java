import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RecordCollection {

    private final long fileLength;
    private final int numberOfBlocks;
    private BufferPoolLRU pool;

    private int blockSize = 4096;
    private int numberOfRecords;

    private int recordSize = 4;


    public RecordCollection(BufferPoolLRU pool, long length) {
        this.pool = pool;
        this.numberOfRecords = (int)(length / recordSize);
        this.fileLength = length;
        this.numberOfBlocks = (int)(length / blockSize);
    }


    public int getNumberOfRecords() {
        return this.numberOfRecords;
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
        ByteBuffer.wrap(resultBytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer()
            .put(shorts);

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

        return new Record(shorts[0], shorts[1]);

    }


    public void writeFirstRecord() {
        int maxRecordsInALine = 8;
        int numberOfRecordInABlock = blockSize / recordSize;

        for (int blockNumber = 0; blockNumber < numberOfBlocks; blockNumber++) {

            int recordNumber = blockNumber * numberOfRecordInABlock;
            Record heapRecord = getHeapRecord(recordNumber);
            System.out.print(heapRecord.getKey() + " " + heapRecord.getValue());
            System.out.print("    ");
            if (--maxRecordsInALine <= 0) {
                System.out.println();
                maxRecordsInALine = 8;
            }

        }

    }

}
