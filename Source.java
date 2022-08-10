import java.io.File;
import java.util.Optional;

import binarytrees.BinaryTreeTest;
import queuedb.QueueDBTest;

/**
 * Main File to run the various test files
 * 
 * @author aangar, 2022
 */
public class Source {
    public static void main(String[] args) {
        Optional<String> flag = Optional.ofNullable(args[0]);
        final String DB_DIR_NAME = args[1] + "/queuedb/sampledb/";
        new File(DB_DIR_NAME).mkdir();
        if (flag.isPresent()) {
            switch (flag.get()) {
                case "tree": {
                    BinaryTreeTest.runTestCases();
                }
                    break;
                case "dynamic": {
                    QueueDBTest.runTests(DB_DIR_NAME);
                }
                    break;

                default:
                    System.out.println("Flag unspecified or unrecognized.");
            }
        }
    }
}
