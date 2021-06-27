import java.io.File;
import java.io.FileNotFoundException;

public class HeapSort {

        public static void main(String[] args) {

            File inputFile = new File(args[0]);
            int bufferSize = Integer.parseInt(args[1]);

            try {
                BufferPool bufferPool = new BufferPool(inputFile, bufferSize);
                RecordCollection recordCollection =
                    new RecordCollection(bufferPool, inputFile.length());
                Hp hp = new Hp(recordCollection);
                hp.sort();

                bufferPool.writeAll();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
}
