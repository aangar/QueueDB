package queuedb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDocumentReader<T extends DatabaseDocument> {

    private Class<T> clazz;
    private T returnable;
    private List<String> fieldNames;

    public DatabaseDocumentReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    private void buildClazz() {
        try {
            Constructor<?> s[] = this.clazz.getDeclaredConstructors();
            Field[] fields = this.clazz.getDeclaredFields();
            List<String> allowedFields = new ArrayList<>();
            allowedFields.add("id");
            for (Field f : fields) {
                String name = f.toString();
                int end = name.lastIndexOf(".") + 1;
                allowedFields.add(name.substring(end, name.length()));
            }
            this.fieldNames = allowedFields;
            this.returnable = (T) s[0].newInstance();
        } catch (InvocationTargetException nsm) {

        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }

    }

    public T readFile(String file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            buildClazz();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                String trimmy = line.trim();
                int ind = trimmy.indexOf("\"", 1);
                if (ind > 0) {
                    String propName = trimmy.substring(1, ind);
                    if (this.fieldNames.contains(propName)) {
                        int startQuoteInd = trimmy.indexOf("\"", ind + 1) + 1;
                        int endQuoteInd = trimmy.indexOf("\"", startQuoteInd + 1);
                        String val = trimmy.substring(startQuoteInd, endQuoteInd);
                        this.returnable.setParsedProperty(propName, val);
                    }
                }
                sb.append("\n");
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("there was an error. uhg.");
            System.out.println(e);
        }

        return this.returnable;
    }
}
