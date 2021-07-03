/**
 * Buffer is a block of memory.
 * One block contains 4096 bytes of data
 * @author mark paes, vishalms
 * @version 1.0
 */
public class Buffer {
    /**
     * data
     */
    private byte[] bytes;
    /**
     * map the file block
     */
    private int blockNumber;
    /**
     * buffer is altered or not
     */
    private boolean isDirty;


    /**
     * Constructor of Buffer to initialise its variable
     * @param bytes number of bytes that buffer contains
     * @param blockNumber buffer block nuber
     */
    public Buffer(byte[] bytes, int blockNumber) {
        this.bytes = bytes;
        this.blockNumber = blockNumber;
        this.isDirty = false;
    }


    /**
     *
      * @return the data in bytes
     */
    public byte[] getBytes() {
        return bytes;
    }


    /**
     * buffer number
     * @return int block number
     */
    public int getBlockNumber() {
        return blockNumber;
    }


    /**
     * Returs True if buffer is altered during the course of
     * sorting else False
     * @return state of the buffer
     */
    public boolean isDirty() {
        return isDirty;
    }


    /**
     * To alter the buffer bytes from starting index and amount of bytes need
     * to be overide
     * @param recordBytes altered bytes
     * @param bufferStart starting index of the buffer
     */
    public void setBytes(byte[] recordBytes, int bufferStart) {

        for (int i = 0; i < recordBytes.length; i++) {
            this.bytes[bufferStart + i] = recordBytes[i];
        }
    }


    /**
     * making buffer dirty stating it has been altered
     */
    public void makeDirty() {
        this.isDirty = true;
    }
}
