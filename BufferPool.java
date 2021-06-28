import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Optional;

public class BufferPool {

    private LinkedList<Buffer> pool;
    private int maxBuffer;

    private int blockSize = 4096;

    private int cacheHitCount = 0;
    private int diskWriteCount = 0;
    private int cacheMissCount = 0;

    private RandomAccessFile file;


    public BufferPool(File file, int maxBuffer) throws FileNotFoundException {
        pool = new LinkedList<>();
        this.file = new RandomAccessFile(file, "rw");;
        this.maxBuffer = maxBuffer;
    }

    public byte[] getRecordBytes(int startIndex, int recordSize) {

        byte[] recordBytes = new byte[recordSize];

        for (int i = 0; i < recordSize; i++) {

            int blockNumber = (startIndex + i) / blockSize;
            Buffer buffer = getBuffer(blockNumber);

            recordBytes[i] =
                buffer.getBytes()[(startIndex + i) - (blockNumber * blockSize)];

        }

        return recordBytes;
    }


    private Buffer getBuffer(int blockNumber) {
        Optional<Buffer> buff = pool.stream().parallel()
            .filter(bf -> bf.getBlockNumber() == blockNumber).findFirst();

        if (buff.isPresent()) {
            Buffer buffer = buff.get();

            // make it LRU
            pool.remove(buffer);
            pool.addFirst(buffer);
            cacheHitCount++;

            return buffer;

        }
        else {
            // not in the pool

            try {
                Buffer buffer = addBuffer(blockNumber);
                return buffer;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    private Buffer addBuffer(int blockNumber) throws IOException {

        Buffer bf;

        // check if buffer is full
        if (maxBuffer == pool.size()) {

            Buffer leastUsedBuffer = pool.removeLast();

            if (leastUsedBuffer.isDirty()) {
                writeBufToFile(leastUsedBuffer);
            }

        }

        byte[] bytes = readBufFromFile(blockNumber);

        bf = new Buffer(bytes, blockNumber);
        pool.addFirst(bf);
        cacheMissCount++;

        return bf;
    }


    private byte[] readBufFromFile(int blockNumber) throws IOException {

        byte[] readBytes = new byte[blockSize];

        int startIndexOfFile = blockNumber * blockSize;
        file.seek(startIndexOfFile);
        file.read(readBytes, 0, blockSize);

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
        pool.stream().forEach(buffer -> {
            if(buffer.isDirty()) {
                try {
                    writeBufToFile(buffer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
