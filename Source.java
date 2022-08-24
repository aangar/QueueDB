import java.io.File;
import java.io.IOException;

import queuedb.QueueDBTest;
import server.BasicServer;

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
            for (int i = 0; i < 11; i++) {
                System.out.println(" ");
            }
            System.out.println("Attempting to start webserver...");
            try {
                BasicServer server = new BasicServer(8080, DB_DIR_NAME + "/sample-documents/");
                server.startServer();
            } catch (IOException IOE) {
                System.out.println(IOE);
            }
        }
        if (args.length < 1) {
            throw new Throwable("DB Directory was not specified, unable to start.");
        }
    }
}
