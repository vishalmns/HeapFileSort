import student.TestCase;

/**
 * test class to check function of Buffer
 * @author mark peas,vishalms
 * @version 1.0
 */
public class BufferTest extends TestCase {

    /**
     * buffer object
     */
    private Buffer buffer;


    /**
     * Set up the buffer with the data.
     */
    public void setUp() {
        buffer = new Buffer("01AA".getBytes(), 0);
    }


    /**
     * Test to fetch bytes from the buffer
     */
    public void testToGetBytes() {
        byte[] bytes = buffer.getBytes();
        assertEquals("01AA", new String(bytes));
    }


    /**
     * Test to fetch particular block number from the buffer.
     */
    public void getBlockNumber() {
        assertEquals(0, buffer.getBlockNumber());
    }


    /**
     * Check weather buffer is dirty or not.
     */
    public void checkDirty() {
        assertFalse(buffer.isDirty());
    }


    /**
     * set buffer dirty
     */
    public void makeDirty() {
        buffer.makeDirty();
        assertTrue(buffer.isDirty());
    }

}
