import student.TestCase;

import java.io.File;
import java.io.IOException;

public class BufferPoolLRUTest extends TestCase {

    ByteFileGenerator generator;
    BufferPoolLRU bufferPool;


    public void setUp() throws IOException {
        generator = new ByteFileGenerator();
        generator.generate(2048);
        File file = new File("p3_input_sample.txt");
        bufferPool = new BufferPoolLRU(file, 1);
    }


    public void testGetRecordBytes() {
        byte[] recordBytes = bufferPool.getRecordBytes(0, 4);
        assertNotNull(recordBytes);
    }


    public void testSetRecordBytes() {
        byte[] bytes = "10AA".getBytes();
        bufferPool.setRecordBytes(bytes, 0);

        byte[] recordBytes = bufferPool.getRecordBytes(0, 4);
        assertNotNull(recordBytes);
    }

}
