import java.io.*;

public class BufferPoolLRU {

    BufferNode head = null;
    private int maxBuffer;

    private int blockSize = 4096;

    private int cacheHitCount = 0;
    private int diskWriteCount = 0;
    private int cacheMissCount = 0;
    private int diskReadCount = 0;
    private int bufferCount = 0;

    private RandomAccessFile file;


    public BufferPoolLRU(File file, int maxBuffer)
        throws FileNotFoundException {
        this.file = new RandomAccessFile(file, "rw");
        this.maxBuffer = maxBuffer;
    }


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


    private Buffer getBuffer(int blockNumber) {
        Buffer result = null;
        BufferNode cur = head;
        while (cur != null) {
            if (cur.buffer.getBlockNumber() == blockNumber) {
                cacheHitCount++;
                result = cur.buffer;
                if (cur != head) {
                    //LRU
                    cur.prev.next = cur.next;
                    if (cur.next != null) {
                        cur.next.prev = cur.prev;
                    }
                    bufferCount--;
                    insertFront(cur);
                }
                break;
            }
            cur = cur.next;
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


    private void insertFront(BufferNode node) {
        bufferCount++;
        if (head == null) {
            head = node;
        }
        else {
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        }
    }


    private Buffer addBuffer(int blockNumber) throws IOException {

        Buffer bf;

        // check if buffer is full
        if (maxBuffer == bufferCount) {
            BufferNode leastUsedNode = head;
            while (leastUsedNode.next != null) {
                leastUsedNode = leastUsedNode.next;
            }

            if (leastUsedNode == head) {
                head = null;
            }
            else {
                leastUsedNode.prev.next = null;
            }
            bufferCount--;
            Buffer leastUsedBuffer = leastUsedNode.buffer;
            if (leastUsedBuffer.isDirty()) {
                writeBufToFile(leastUsedBuffer);
            }

        }

        byte[] bytes = readBufFromFile(blockNumber);

        bf = new Buffer(bytes, blockNumber);
        insertFront(new BufferNode(bf));
        cacheMissCount++;

        return bf;
    }


    private byte[] readBufFromFile(int blockNumber) throws IOException {

        byte[] readBytes = new byte[blockSize];

        int startIndexOfFile = blockNumber * blockSize;
        file.seek(startIndexOfFile);
        file.read(readBytes, 0, blockSize);
        diskReadCount++;
        return readBytes;
    }


    private void writeBufToFile(Buffer leastUsedBuffer) throws IOException {

        byte[] bytes = leastUsedBuffer.getBytes();
        int startIndexOfFile = leastUsedBuffer.getBlockNumber() * blockSize;

        file.seek(startIndexOfFile);
        file.write(bytes);
        diskWriteCount++;

        //  Try this
//        file.write(bytes,startIndexOfFile,blockSize);
    }


    public void setRecordBytes(byte[] bytes, int index) {

        int blockNumber = index / blockSize;

        Buffer buffer = getBuffer(blockNumber);

        int bufferStart = index % blockSize;

        buffer.setBytes(bytes, bufferStart);

        buffer.makeDirty();

    }


    public void writeAll() {

        while (head != null) {
            Buffer buffer = head.buffer;
            if (buffer.isDirty()) {
                try {
                    writeBufToFile(buffer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            head = head.next;
            bufferCount = 0;
        }
    }


    public void writeStats(String statFileName) {
        try {
            FileWriter fw = new FileWriter(statFileName, true);
            fw.write("------  STATS ------" + "\n");
            fw.write("File name: " + statFileName + "\n");
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
