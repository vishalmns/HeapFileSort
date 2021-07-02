import student.TestCase;

import java.io.*;

public class RecordCollectionTest extends TestCase {

    RecordCollection recordCollection;


    public void setUp() throws IOException {
        File actualTestFile = new File("test_one_buf_one_rec.txt");
        File copyTestFile = new File("test.txt");
        copyFileUsingStream(actualTestFile, copyTestFile);
        BufferPoolLRU pool = new BufferPoolLRU(copyTestFile, 1);
        recordCollection = new RecordCollection(pool, copyTestFile.length());
    }


    public void testNumberOfRecordInOneRecFile() {
        int numberOfRecords = recordCollection.getNumberOfRecords();
        assertEquals(1024, numberOfRecords);
    }


    public void testGetParticularHeapRecord() {
        Record heapRecord = recordCollection.getHeapRecord(10);
        assertEquals((short)8505, heapRecord.getKey());
        assertEquals((short)16276, heapRecord.getValue());
    }


    public void testGetParticularHeapRecordNotInFile() {
        Record heapRecord = recordCollection.getHeapRecord(10000);
        assertEquals((short)0, heapRecord.getKey());
        assertEquals((short)0, heapRecord.getValue());
    }


    public void testSwapHeapRecord() {
        Record heapRecordZero = recordCollection.getHeapRecord(0);
        Record heapRecordOne = recordCollection.getHeapRecord(1);
        recordCollection.swap(0, 1);
        assertEquals(heapRecordOne.getKey(),
            recordCollection.getHeapRecord(0).getKey());
        assertEquals(heapRecordZero.getKey(),
            recordCollection.getHeapRecord(1).getKey());
        assertEquals(heapRecordOne.getValue(),
            recordCollection.getHeapRecord(0).getValue());
        assertEquals(heapRecordZero.getValue(),
            recordCollection.getHeapRecord(1).getValue());
    }


    private void copyFileUsingStream(File source, File dest)
        throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
        finally {
            is.close();
            os.close();
        }
    }

}
