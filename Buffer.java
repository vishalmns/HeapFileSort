import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Buffer {

    private byte[] bytes;
    private int blockNumber;
    private boolean isDirty;

    public Buffer(byte[] bytes, int blockNumber) {
        this.bytes = bytes;
        this.blockNumber = blockNumber;
        this.isDirty = false;
    }


    public byte[] getBytes() {
        return bytes;
    }


    public int getBlockNumber() {
        return blockNumber;
    }


    public boolean isDirty() {
        return isDirty;
    }


    public short getValue(int bufferIndex) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(bytes[bufferIndex]);
        bb.put(bytes[bufferIndex + 1]);
        return bb.getShort(0);
    }


    public void setBytes(byte[] recordBytes, int bufferStart) {

        for (int i = 0; i < recordBytes.length; i++) {
            this.bytes[bufferStart + i] = recordBytes[i];
        }
    }


    public void makeDirty() {
        this.isDirty = true;
    }
}
