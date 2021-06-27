public class Record {
    private short key;
    private short value;

    public Record(short key, short value) {
        this.key = key;
        this.value = value;
    }


    public Record(byte[] recordBytes) {

    }


    public short getKey() {
        return key;
    }


    public short getValue() {
        return value;
    }
}
