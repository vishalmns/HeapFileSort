public class Verifier {

    public static void main(String[] args) {

        CheckFile checkFile = new CheckFile();
        try {
            boolean b = checkFile.checkFile("p3_input_sample.txt");
            System.out.println(b);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
