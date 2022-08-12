import java.io.File;
import queuedb.QueueDBTest;

/**
 * Main File to run the various test files
 * 
 * @author aangar, 2022
 */
public class Source {
    public static void main(String[] args) throws Throwable{
        if (args.length > 0){ 
            final String DB_DIR_NAME = args[0] + "/queuedb/sampledb/";
            new File(DB_DIR_NAME).mkdir();
            QueueDBTest.runTests(DB_DIR_NAME);
        }
        if (args.length < 1) {
            throw new Throwable("DB Directory was not specified, unable to start.");
        }
    }
}
