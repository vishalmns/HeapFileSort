import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Generator {

    public static void main(String[] args) throws IOException {

        ByteFileGenerator generator = new ByteFileGenerator();
        int numberOfRecords = 1024 * 2;
        generator.generate(numberOfRecords);

        generate(numberOfRecords);

        File fp = new File("p3_input_sample.txt");
        System.out.println(fp.length());


    }
    public static void generate(int numRecords) throws IOException {

        File file = new File("input.txt");
        DataOutputStream outs = new DataOutputStream(new FileOutputStream(file,
                false));
        for (int j = 0; j < numRecords; j++) {
            short key = (short)(Math.random() * Short.MAX_VALUE);
            short val = (short)(Math.random() * Short.MAX_VALUE);
            byte[] testB = new byte[4];
            for (int i = 0; i < 2; i++) {
                testB[i] = (byte)(key >> (8 - 8 * i));
                testB[i + 2] = (byte)(val >> (8 - 8 * i));
            }

            outs.write(testB);
        }
        outs.close();
    }

}

