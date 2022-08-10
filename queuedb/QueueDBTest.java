package queuedb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for test methods related to queues.
 * <br>
 * </br>
 * What this does as of now:
 * <ol>
 * <li>Takes in a list of names.</li>
 * <li>Coverts said names into a DynamicQueue (the queue with target type).</li>
 * <li>Ensures the DB folder exists. If not, creates it.</li>
 * <li>Ensures there are no documents saved. If there are, purges them./li>
 * <li>Converts documents to JSON and saved them locally./li>
 * <li>Reads all files, and converts them into the target type./li>
 * </ol>
 * 
 * @author aangar, 2022
 */
public class QueueDBTest {
    public static void runTests(String dir) {
        List<SampleDocument> smpDocs = List.of("alpha", "beta", "ceta", "delta")
                .stream()
                .map(SampleDocument::convertToSampleDoc)
                .collect(Collectors.toList());
        DynamicQueue<SampleDocument> docsQueue = new DynamicQueue<SampleDocument>(smpDocs);

        new File(dir).mkdir();
        File baseDir = new File(dir);
        String[] existingFiles = baseDir.list();
        if (existingFiles.length > 0) {
            for (String file : existingFiles) {
                new File(dir + file).delete();
            }
        }

        while (docsQueue.getTotalDocs() > 0) {
            SampleDocument d = docsQueue.process();
            try {
                FileWriter writer = new FileWriter(dir + d.getName() + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"id\": \"%s\",\n", d.getId()));
                writer.write(String.format("   \"name\": \"%s\",\n", d.getName()));
                writer.write(String.format("   \"generationDate\": \"%s\"\n", d.getGenerationDate()));
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }

        File b = new File(dir);
        String[] e = b.list();

        if (e.length > 0) {
            for (String file : e) {
                DatabaseParser<SampleDocument> fileRead = new DatabaseParser<SampleDocument>(
                        SampleDocument.class);
                SampleDocument parsed = fileRead.readFile(dir + file);
            }
        }
    }
}