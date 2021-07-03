import student.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * Test the BufferPool
 * @author mark peas,vishalms
 * @version 1.0
 */
public class BufferPoolLRUTest extends TestCase {

    /**
     * Buffer pool object
     */
    private BufferPoolLRU bufferPool;


    /**
     * generating data and initalised object for test
     * @throws IOException throw error when unable generate data
     */
    public void setUp() throws IOException {
        ByteFileGenerator generator = new ByteFileGenerator();
        generator.generate(2048);
        File file = new File("p3_input_sample.txt");
        bufferPool = new BufferPoolLRU(file, 1);
    }


    /**
     * test to get the record bytes from the buffer pool
     */
    public void testGetRecordBytes() {
        byte[] recordBytes = bufferPool.getRecordBytes(0, 4);
        assertNotNull(recordBytes);
    }


    /**
     * test to set the record bytes to the buffer pool
     */
    public void testSetRecordBytes() {
        byte[] bytes = "10AA".getBytes();
        bufferPool.setRecordBytes(bytes, 0);

        byte[] recordBytes = bufferPool.getRecordBytes(0, 4);
        assertNotNull(recordBytes);
    }

}
