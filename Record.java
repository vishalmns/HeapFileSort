/**
 * sorting file contains n records.
 * @author mark paes, vishalms
 * @version 1.0
 */
public class Record {
    /**
     * Key of the record
     * 2 bytes
     */
    private short key;
    /**
     * value of the record
     * 2 bytes
     */
    private short value;


    /**
     * constructor to intialised the record
     * @param key key of the record
     * @param value value of the record
     */
    public Record(short key, short value) {
        this.key = key;
        this.value = value;
    }


    /**
     *
     * @return the key of the record
     */
    public short getKey() {
        return key;
    }


    /**
     *
     * @return the value of the record
     */
    public short getValue() {
        return value;
    }
}
