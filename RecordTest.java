import student.TestCase;

/**
 * Test Record Class
 *
 * @author mark paes, vishalms
 * @version 1.0
 */
public class RecordTest extends TestCase {

    /**
     * Record object
     */
    private Record record;


    /**
     * initialise the record object.
     */
    public void setUp() {
        record = new Record((short)123, (short)321);
    }


    /**
     * test to fetch the key of the record.
     */
    public void testGetKey() {
        assertEquals(123, record.getKey());
    }


    /**
     * test to fetch the value of the record
     */
    public void testGetValue() {
        assertEquals(321, record.getValue());
    }
}
