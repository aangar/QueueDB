package server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpServer;

import queuedb.DAO.SampleDocumentDAO;
import queuedb.Objects.SampleDocument;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class BasicServer{
    private final int port;
    private final SampleDocumentDAO sampDocDAO;

    public BasicServer(int port, String DIR) {
        this.port = port;
        this.sampDocDAO = new SampleDocumentDAO(DIR);
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.createContext("/api", t -> {
            SampleDocument doc = SampleDocument.convertToSampleDoc("TETRA_BETA_DOPAMINE");
            Optional<SampleDocument> saved = this.sampDocDAO.saveOne(doc);
            if (saved.isPresent()) {
                ObjectToJSON<SampleDocument> converter = new ObjectToJSON<>(SampleDocument.class);
                converter.convert();
            }
            String response = saved.get().toString();
            t.setAttribute("doof", "boof");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
    }

    private class ObjectToJSON<T> {
        private String clazz;

        public ObjectToJSON(Class<T> cn) {
            this.clazz = cn.getName();
        }
        private void convert() {
            System.out.println(this.clazz);
            try {
                Class<?> instance = Class.forName(this.clazz);
                Method[] methods = instance.getClass().getDeclaredMethods();
                List<Method> getters = List.of(methods).stream().filter(x -> x.getName().contains("get")).collect(Collectors.toList());
                Constructor<?> consts[] = instance.getDeclaredConstructors();
                T invokeable = (T) consts[0].newInstance();
                for (Method m : getters) {
                    System.out.println(m.invoke(invokeable));
                }
            } catch (ClassNotFoundException CNFE) {
                CNFE.printStackTrace();
            } catch (InstantiationException IE) {
                IE.printStackTrace();
            } catch (IllegalAccessException IAE) {
                IAE.printStackTrace();
            } catch (InvocationTargetException ITE) {
                ITE.printStackTrace();
            }
        }
    }
}
