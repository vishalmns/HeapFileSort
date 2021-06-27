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
        ByteBuffer byteBufferKey = ByteBuffer.allocate(2);
        byteBufferKey.putShort(record.getKey());
        byte[] keyByteArray = byteBufferKey.array();

        ByteBuffer byteBufferValue = ByteBuffer.allocate(2);
        byteBufferValue.putShort(record.getValue());
        byte[] valueByteArray = byteBufferValue.array();

        byte[] resultByteArray =
            new byte[keyByteArray.length + valueByteArray.length];

        int index = 0;
        for (int i = 0; i < keyByteArray.length; i++) {
            resultByteArray[index++] = keyByteArray[i];
        }
        for (int i = 0; i < valueByteArray.length; i++) {
            resultByteArray[index++] = valueByteArray[i];
        }

        return resultByteArray;

    }


    public Record getHeapRecord(int recordNumber) {

        int index = recordNumber * recordSize;

        byte[] recordBytes = pool.getRecordBytes(index, recordSize);

        Record record = buildRecord(recordBytes);

        return record;
    }

    private Record buildRecord(byte[] recordBytes) {
        ByteBuffer byteBufferKey = ByteBuffer.allocate(2);
        byteBufferKey.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferKey.put(recordBytes[0]);
        byteBufferKey.put(recordBytes[1]);
        short key = byteBufferKey.getShort(0);

        ByteBuffer byteBufferValue = ByteBuffer.allocate(2);
        byteBufferValue.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferValue.put(recordBytes[2]);
        byteBufferValue.put(recordBytes[3]);
        short value = byteBufferValue.getShort(0);

        return new Record(key,value);
    }

}
