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
import queuedb.Objects.DatabaseDocument;
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
                ObjectToJSON<SampleDocument> converter = new ObjectToJSON<>();
                String result = converter.convert(saved.get());
            }
            String response = "boofus doofus youve been snoofues";
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });
        server.setExecutor(null);
        server.start();
    }

    public class ObjectToJSON<T> {
        public ObjectToJSON() { }

        public String convert(T responseDocument) {
            try {
                List<Method> methods = List.of(responseDocument.getClass().getMethods())
                    .stream()
                    .filter(x -> x.getName().contains("get") && !x.getName().contains("getClass"))
                    .collect(Collectors.toList());
                for (Method method : methods) {
                    System.out.println(method.invoke(responseDocument));
                }
                
            } catch (IllegalAccessException IAE) {
                IAE.printStackTrace();
            } catch (InvocationTargetException ITE) {
                ITE.printStackTrace();
            }
            return "alinine";
        }
    }
}
