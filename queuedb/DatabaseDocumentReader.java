package queuedb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class DatabaseDocumentReader<T extends SampleDocument> {

    private Class<T> clazz;
    private T returnable;

    public DatabaseDocumentReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    private void buildClazz() {
        try {   
            Constructor<?> s[] = this.clazz.getDeclaredConstructors();
            this.returnable = (T) s[0].newInstance();
        } catch (InvocationTargetException nsm) {

        } catch (InstantiationException ie) {
            
        } catch (IllegalAccessException iae) {
            
        }

    }

    public T readFile(String file, List<String> keys) { 
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            buildClazz();
            System.out.println(this.returnable.getId());
            while ((line = br.readLine()) != null) {
                sb.append(line);
                String trimmy = line.trim();
                int ind = trimmy.indexOf("\"", 1);
                if (ind > 0) {
                    String propName = trimmy.substring(1, ind);
                    if (keys.contains(propName)) {
                        int startQuoteInd = trimmy.indexOf("\"", ind + 1) +1;
                        int endQuoteInd = trimmy.indexOf("\"", startQuoteInd + 1);
                        String val = trimmy.substring(startQuoteInd, endQuoteInd);
                        switch (propName) {
                            case "id": returnable.setId(val); break;
                            case "name" : returnable.setName(val); break;
                            case "generationDate": returnable.setGenerationDate(val); break;
                            default: System.out.println("Property name not recognized.");
                        }
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
