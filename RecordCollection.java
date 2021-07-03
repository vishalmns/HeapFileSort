import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Class contains the collection of records of the file
 * Ability to get record via buffer pool
 * @author mark paes,vishalms
 * @version 1.0
 */
public class RecordCollection {

    /**
     * Total number of blocks
     */
    private final int numberOfBlocks;
    /**
     * Buffer pool to access buffer
     */
    private BufferPoolLRU pool;
    /**
     * each block size
     */
    private int blockSize = 4096;
    /**
     * total number of records
     */
    private int numberOfRecords;

    /**
     * Size of each record
     */
    private int recordSize = 4;


    /**
     * Contructor to initalise the Record collections variables
     * @param pool buffer pool
     * @param length length of the file
     */
    public RecordCollection(BufferPoolLRU pool, long length) {
        this.pool = pool;
        this.numberOfRecords = (int)(length / recordSize);
        this.numberOfBlocks = (int)(length / blockSize);
    }


    /**
     * get the total number of the records
     * @return records size
     */
    public int getNumberOfRecords() {
        return this.numberOfRecords;
    }


    /**
     * swap two records data
     * @param a starting index of record a
     * @param b starting index of record b
     */
    public void swap(int a, int b) {
        Record heapRecordA = this.getHeapRecord(a);
        Record heapRecordB = this.getHeapRecord(b);
        this.setHeapRecord(heapRecordA, b);
        this.setHeapRecord(heapRecordB, a);
    }


    /**
     * converting record to bytes and writting to pool
     * @param record Written record
     * @param recordNumber its record number
     */
    private void setHeapRecord(Record record, int recordNumber) {
        int index = recordNumber * recordSize;

        byte[] bytes = buildBytes(record);
        pool.setRecordBytes(bytes, index);

    }


    /**
     * convert Record to bytes
     * @param record record that needed to converted
     * @return bytes of the record
     */
    private byte[] buildBytes(Record record) {

        short[] shorts = new short[] { record.getKey(), record.getValue() };

        byte[] resultBytes = new byte[shorts.length * 2];
        ByteBuffer.wrap(resultBytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer()
            .put(shorts);

        return resultBytes;

    }


    /**
     * Get the particular record from its number
     * @param recordNumber record number
     * @return record of that number
     */
    public Record getHeapRecord(int recordNumber) {

        int index = recordNumber * recordSize;

        byte[] recordBytes = pool.getRecordBytes(index, recordSize);

        Record record = buildRecord(recordBytes);

        return record;
    }


    /**
     * Convert bytes to the record
     * @param recordBytes bytes of the record
     * @return record
     */
    private Record buildRecord(byte[] recordBytes) {
        short[] shorts = new short[recordBytes.length / 2];

        ByteBuffer.wrap(recordBytes).order(ByteOrder.BIG_ENDIAN).asShortBuffer()
            .get(shorts);

        return new Record(shorts[0], shorts[1]);

    }


    /**
     * write all first record data of each block
     */
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
