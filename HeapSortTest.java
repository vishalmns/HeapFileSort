import student.TestCase;

import java.io.*;

/**
 * Test to heap sort of the file
 * @author mark paes,vishalms
 * @version 1.0
 */
public class HeapSortTest extends TestCase {
    /**
     * to fetch standard output data in test
     */
    private final ByteArrayOutputStream outputStreamCaptor =
        new ByteArrayOutputStream();
    /**
     * Heap sort object
     */
    private HeapSort heapSort;
    /**
     * Ojject used to verify the file is sorted or not.
     */
    private CheckFile checkFile;


    /**
     * initalise the declared object.
     */
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        heapSort = new HeapSort();
        checkFile = new CheckFile();
    }


    /**
     * test to throw expection when number of aruuments is not equal to three
     */
    public void testToThrowIllegalArgumentExpectionWhenArgumentLessThanThree() {
        String[] strings = new String[] { "one" };
        HeapSort.main(strings);
        assertEquals("expected argument length 3",
            outputStreamCaptor.toString().trim());
    }


    /**
     * Test to file is sorted of size 4096 bytes with help of one buffer
     * @throws Exception when unable to do file operation
     */
    public void testToSortAFile() throws Exception {
        File actualTestFile = new File("test_one_buf_one_rec.txt");
        File copyTestFile = new File("test.txt");
        copyFileUsingStream(actualTestFile, copyTestFile);
        String[] args = new String[] { "test.txt", "1", "stats.txt" };
        heapSort.main(args);

        boolean result = checkFile.checkFile("test.txt");
        assertTrue(result);
        assertTrue(outputStreamCaptor.toString().contains("99 31801"));

        boolean delete = copyTestFile.delete();
        assertTrue(delete);
    }


    /**
     * Test to file is sorted of size 4096 * 10 bytes with help of one buffer
     * @throws Exception when unable to do file operation
     */
    public void testToSortAFileWithOneBufferMoreRecord() throws Exception {
        File actualTestFile = new File("test_one_buf_ten_rec.txt");
        File copyTestFile = new File("test.txt");
        copyFileUsingStream(actualTestFile, copyTestFile);
        String[] args = new String[] { "test.txt", "1", "stats.txt" };
        heapSort.main(args);

        boolean result = checkFile.checkFile("test.txt");
        assertTrue(result);
        assertEquals(oneBufTenRec(), outputStreamCaptor.toString().trim());

        boolean delete = copyTestFile.delete();
        assertTrue(delete);
    }

    /**
     * Test to file is sorted of size 4096 * 100 bytes with help of max buffer
     * @throws Exception when unable to do file operation
     */
    public void testToSortAFileWith20BufferWithMaxRecord() throws Exception {
        File actualTestFile = new File("test_max_buffer.txt");
        File copyTestFile = new File("test.txt");
        copyFileUsingStream(actualTestFile, copyTestFile);
        String[] args = new String[] { "test.txt", "20", "stats.txt" };
        heapSort.main(args);

        boolean result = checkFile.checkFile("test.txt");
        assertTrue(result);
        assertEquals(maxStringOutput().trim(),
            outputStreamCaptor.toString().trim());

        boolean delete = copyTestFile.delete();
        assertTrue(delete);
    }


    /**
     * To make a copy of test file so that repeated running of test should not
     * change the value
     * @param source actual test generated file
     * @param dest copy of the acutal file
     * @throws IOException throws when unable to access the file
     */
    private void copyFileUsingStream(File source, File dest)
        throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
        finally {
            assert is != null;
            is.close();
            assert os != null;
            os.close();
        }
    }


    /**
     * Stream output when sorted with max buffer test
     * @return starting key of each buffer
     */
    private String maxStringOutput() {
        return "0 32273    331 29628    654 7456    992 7948    1318 22179   "
            + " 1650 21539    1960 16358    2283 13303    \n"
            + "2598 26162    2920 14878    3267 31326    3590 5578   "
            + " 3905 25861    4246 5866    4566 10542    4886 12082    \n"
            + "5215 3152    5556 13916    5869 20574    6211 29410   "
            + " 6537 22365    6843 24155    7163 1    7478 12954    \n"
            + "7813 26862    8129 19195    8459 16790    8780 5067   "
            + " 9109 23414    9447 17454    9770 10529    10106 24935    \n"
            + "10448 20745    10790 28344    11119 382    11447 10409  "
            + "  11772 15313    12093 25564    12426 28912    12766 3194    \n"
            + "13096 17945    13423 9727    13746 16654    14096 15834  "
            + "  14421 30782    14762 13914    15071 28197    15403 30306    \n"
            + "15716 5976    16040 1591    16367 26388    16701 27043 "
            + "   17015 5475    17344 12952    17662 18128    17974 1480    \n"
            + "18295 12096    18640 9684    18981 5624    19320 12998  "
            + "  19658 9987    20003 13279    20326 5085    20651 439    \n"
            + "20967 6916    21320 22202    21645 18545    21978 277  "
            + "  22316 5938    22628 25493    22935 27998    23272 40    \n"
            + "23612 1602    23936 32401    24249 32502    24576 30463  "
            + "  24909 5903    25230 5492    25551 146    25890 27597    \n"
            + "26206 17500    26533 12020    26848 23077    27180 18330 "
            + "   27496 8225    27813 20611    28144 6042    28484 16156    \n"
            + "28825 30208    29141 18495    29464 8599    29803 25274  "
            + "  30118 23875    30448 30253    30778 16251    31091 27362    \n"
            + "31414 16713    31769 12166    32091 11696    32434 4848";
    }


    /**
     * stream output when sorted with one buffer of size 4096*10 bytes file
     * @return starting key of each buffer
     */
    public String oneBufTenRec() {
        return
            "0 24749    3242 12340    6623 12552    9803 2677    13266 30182"
                + "    16518 24772    19751 4473    23140 12206    \n"
                + "26335 19455    29480 11115";
    }
}
