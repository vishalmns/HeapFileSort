import java.io.File;
import java.io.FileNotFoundException;

public class HeapSort {

        public static void main(String[] args) {

            File inputFile = new File(args[0]);
            int bufferSize = Integer.parseInt(args[1]);
            String statFileName = args[2];

            try {
                BufferPool bufferPool = new BufferPool(inputFile, bufferSize);
                System.out.println(inputFile.length());
                RecordCollection recordCollection =
                    new RecordCollection(bufferPool, inputFile.length());
                Sorter sorter = new Sorter(recordCollection);
                sorter.sort();

                bufferPool.writeAll();
                bufferPool.writeStats(statFileName);
                sorter.writeTimeTaken(statFileName);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
}
