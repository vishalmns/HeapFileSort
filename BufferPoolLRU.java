import java.io.*;

/**
 * A class to maintain the all the buffer and
 * Contains n buffer with head node
 * Allows operation on buffer
 * @author mark paes, vishalms
 * @version 1.0
 */
public class BufferPoolLRU {
    /**
     * Head node of the Buffer
     */
    private BufferNode head = null;
    /**
     * Max buffer it can hold
     */
    private int maxBuffer;
    /**
     * Block size of each buffer
     */
    private int blockSize = 4096;

    /**
     * counter for basic operation
     * on the buffer
     */
    private int cacheHitCount = 0;
    private int diskWriteCount = 0;
    private int cacheMissCount = 0;
    private int diskReadCount = 0;
    private int bufferCount = 0;

    /**
     * File on which buffer pool works on
     */
    private RandomAccessFile file;


    /**
     * Constustor to initalise the buffer pool
     * @param file file that need to be sorted
     * @param maxBuffer max buffer size
     * @throws FileNotFoundException throws unable to access the file
     */
    public BufferPoolLRU(File file, int maxBuffer)
        throws FileNotFoundException {
        this.file = new RandomAccessFile(file, "rw");
        this.maxBuffer = maxBuffer;
    }


    /**
     * Return the bytes in file with help of buffer pool
     * @param startIndex index of byte in the file
     * @param recordSize number of byte that need to be in record
     * @return bytes requested
     */
    public byte[] getRecordBytes(int startIndex, int recordSize) {

        byte[] recordBytes = new byte[recordSize];
        Buffer buffer = null;

        for (int i = 0; i < recordSize; i++) {

            int blockNumber = (startIndex + i) / blockSize;

            if (buffer == null) {
                buffer = getBuffer(blockNumber);
            }
            recordBytes[i] =
                buffer.getBytes()[(startIndex + i) - (blockNumber * blockSize)];

        }

        return recordBytes;
    }


    /**
     * Return Buffer that contains a particular block
     * @param blockNumber requested buffer block number
     * @return buffer
     */
    private Buffer getBuffer(int blockNumber) {
        Buffer result = null;
        BufferNode cur = head;
        while (cur != null) {
            if (cur.getBuffer().getBlockNumber() == blockNumber) {
                cacheHitCount++;
                result = cur.getBuffer();
                if (cur != head) {
                    //LRU operation
                    cur.getPrev().setNext(cur.getNext());
                    if (cur.getNext() != null) {
                        cur.getNext().setPrev(cur.getPrev());
                    }
                    // decrease total buffer count
                    bufferCount--;
                    // insert the recently used node at front
                    insertFront(cur);
                }
                break;
            }
            cur = cur.getNext();
        }

        if (result != null) {
            return result;
        }

        try {
            result = addBuffer(blockNumber);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Insert front to the doubly linked list containing Buffers
     * @param node Buffer node
     */
    private void insertFront(BufferNode node) {
        // increase the count of the buffer
        bufferCount++;
        if (head == null) {
            head = node;
        }
        else {
            node.setPrev(null);
            node.setNext(head);
            head.setPrev(node);
            head = node;
        }
    }


    /**
     * Add particular buffer of give block number
     * @param blockNumber block number of the buffer needed to added
     * @return buffer that is added to LRU
     * @throws IOException throws when old buffer is not able to write to file
     */
    private Buffer addBuffer(int blockNumber) throws IOException {

        Buffer bf;

        // check if buffer is full
        if (maxBuffer == bufferCount) {
            BufferNode leastUsedNode = head;
            while (leastUsedNode.getNext() != null) {
                leastUsedNode = leastUsedNode.getNext();
            }

            if (leastUsedNode == head) {
                head = null;
            }
            else {
                leastUsedNode.getPrev().setNext(null);
            }
            bufferCount--;
            Buffer leastUsedBuffer = leastUsedNode.getBuffer();

            if (leastUsedBuffer.isDirty()) {
                // write the dirty buffer to the file
                writeBufToFile(leastUsedBuffer);
            }

        }

        byte[] bytes = readBufFromFile(blockNumber);

        bf = new Buffer(bytes, blockNumber);
        insertFront(new BufferNode(bf));
        cacheMissCount++;

        return bf;
    }


    /**
     * Read a bytes from the file
     * @param blockNumber the block number in which the file located
     * @return a block size bytes of data
     * @throws IOException unable to access the file
     */
    private byte[] readBufFromFile(int blockNumber) throws IOException {

        byte[] readBytes = new byte[blockSize];

        int startIndexOfFile = blockNumber * blockSize;
        file.seek(startIndexOfFile);
        file.read(readBytes, 0, blockSize);
        diskReadCount++;
        return readBytes;
    }


    /**
     * Write the dirty buffer to the file
     * @param leastUsedBuffer least used buffer in the cache
     * @throws IOException unable to write the data to file
     */
    private void writeBufToFile(Buffer leastUsedBuffer) throws IOException {

        byte[] bytes = leastUsedBuffer.getBytes();
        int startIndexOfFile = leastUsedBuffer.getBlockNumber() * blockSize;

        file.seek(startIndexOfFile);
        file.write(bytes);
        diskWriteCount++;

    }


    /**
     * Write alerated record to the buffer
     * @param bytes data that needed to be changed in the buffer
     * @param index starting index of the buffer.
     */
    public void setRecordBytes(byte[] bytes, int index) {

        // calculate the block number of the index record
        int blockNumber = index / blockSize;

        Buffer buffer = getBuffer(blockNumber);

        // starting index of the buffer
        int bufferStart = index % blockSize;

        buffer.setBytes(bytes, bufferStart);

        buffer.makeDirty();

    }


    /**
     * After sorting write all dirty buffer to the file
     */
    public void writeAll() {

        while (head != null) {
            Buffer buffer = head.getBuffer();
            if (buffer.isDirty()) {
                try {
                    writeBufToFile(buffer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            head = head.getNext();
            bufferCount = 0;
        }
    }


    /**
     * write statistics to file conating cache hit, cache miss, disk reads,
     * disk writes
     * @param statFileName name of the file to be written
     * @param inputFile sort file
     */
    public void writeStats(String statFileName, File inputFile) {
        try {
            FileWriter fw = new FileWriter(statFileName, true);
            fw.write("------  STATS ------" + "\n");
            fw.write("File name: " + inputFile.getName() + "\n");
            fw.write("Cache Hits: " + cacheHitCount + "\n");
            fw.write("Cache Misses: " + cacheMissCount + "\n");
            fw.write("Disk Reads: " + diskReadCount + "\n");
            fw.write("Disk Writes: " + diskWriteCount + "\n");
            fw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
