package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class BasicServer{
    private final int port;

    public BasicServer(int port) {
        this.port = port;
    }

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
        server.createContext("/api", new Handler());
        server.setExecutor(null);
        server.start();
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "{\"value\" : \"pair\"}";
            System.out.println(t.getRequestMethod());
            t.setAttribute("doof", "boof");
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
