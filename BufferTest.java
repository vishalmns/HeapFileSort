import student.TestCase;

public class BufferTest extends TestCase {

    Buffer buffer;


    public void setUp() {
        buffer = new Buffer("01AA".getBytes(), 0);
    }


    public void testToGetBytes() {
        byte[] bytes = buffer.getBytes();
        assertEquals("01AA", new String(bytes));
    }


    public void getBlockNumber() {
        assertEquals(0, buffer.getBlockNumber());
    }


    public void checkDirty() {
        assertFalse(buffer.isDirty());
    }


    public void makeDirty() {
        buffer.makeDirty();
        assertTrue(buffer.isDirty());
    }

}
