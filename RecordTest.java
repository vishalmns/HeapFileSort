import student.TestCase;

public class RecordTest extends TestCase {

    Record record;


    public void setUp() {
        record = new Record((short)123, (short)321);
    }


    public void testGetKey() {
        assertEquals(123, record.getKey());
    }


    public void testGetValue() {
        assertEquals(321, record.getValue());
    }
}
