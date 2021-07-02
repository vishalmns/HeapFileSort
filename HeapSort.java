import java.io.File;

public class HeapSort {

    public static void main(String[] args) {
        try {

            if (args.length != 3) {
                throw new IllegalArgumentException(
                    "expected argument length 3");
            }

            File inputFile = new File(args[0]);
            int bufferSize = Integer.parseInt(args[1]);
            String statFileName = args[2];
            BufferPoolLRU bufferPool = new BufferPoolLRU(inputFile, bufferSize);
            RecordCollection recordCollection =
                new RecordCollection(bufferPool, inputFile.length());
            Sorter sorter = new Sorter(recordCollection);
            sorter.sort();

            bufferPool.writeAll();
            bufferPool.writeStats(statFileName);
            recordCollection.writeFirstRecord();
            sorter.writeTimeTaken(statFileName);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
