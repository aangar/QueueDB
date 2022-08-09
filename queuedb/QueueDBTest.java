package queuedb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for test methods related to queues
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
                DatabaseDocumentReader<SampleDocument> fileRead = new DatabaseDocumentReader<SampleDocument>(
                        SampleDocument.class);
                SampleDocument parsed = fileRead.readFile(dir + file);
            }
        }
    }
}