package queuedb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Class for test methods related to queues
 * 
 * @author aangar, 2022
 */
public class QueueDBTest {
    public static void runTests(String dir) {
        Queue<Doc> returned = genDocList(List.of("alpha", "beta", "charlie", "delta"));
        File baseDir = new File(dir);
        String[] existingFiles = baseDir.list();
        if (existingFiles.length > 0) {
            for (String file : existingFiles) {
                new File(dir + file).delete();
            }
        }
        new File(dir).mkdir();
        while (returned.size() > 0) {
            Doc d = returned.poll();
            try {
                FileWriter writer = new FileWriter(dir + d.getName() + ".json");
                writer.write("{\n");
                writer.write(String.format("   \"name\": \"%s\",\n", d.getName()));
                writer.write(String.format("   \"id\": \"%s\"\n", d.getUUID()));
                writer.write("}");
                writer.close();
            } catch (IOException e) {
                System.out.println("damn there was an error");
                System.out.println(e);
            }
        }
    }

    public static Queue<Doc> genDocList(List<String> names) {
        Queue<Doc> converted = new LinkedList<>(
                names.stream()
                        .map(Doc::convertToDoc)
                        .collect(Collectors.toList()));
        return converted;
    }

    /**
     * Some class made for testing.
     * 
     * @author aangar, 2022
     */
    private static class Doc {
        private String name;
        private String UUID;

        public Doc(String name, String uuid) {
            this.name = name;
            this.UUID = uuid;
        }

        /**
         * makes a name into a doc class
         * 
         * @param name name
         * @return converted name to a valid document
         */
        private static Doc convertToDoc(String name) {
            int steps = (int) Math.floor(Math.random() * 2) + 2;
            char[] letters = { 'q', 'e', 't', 'u', 'o', 'a' };
            String UUID = "";
            for (int i = 0; i < steps * 4; i++) {
                if (i % 4 == 0 && i != 0) {
                    UUID = UUID + "-";
                }
                if (i % 2 == 0) {
                    int val = (int) Math.floor(Math.random() * letters.length);
                    UUID = UUID + letters[val];
                } else {
                    UUID = UUID + (int) Math.floor(Math.random() * 9);
                }
            }
            return new Doc(name, UUID);
        }

        private String getName() {
            return this.name;
        }

        private String getUUID() {
            return this.UUID;
        }
    }
}