package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collections;


/**
 * Hello world!
 */
public class App {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(App.class);
    static HttpServer server;
    public static void main(String[] args) {
        logger.info("Start slf4j");
        try {
            startServer();
            System.out.println("Press enter to close");
            System.in.read();
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
        stopServer();
    }

    static void stopServer() {
        server.stop(1);
        logger.info("Stopped server");
    }

    public static void startServer() throws IOException {
        // Create an HttpServer instance
        server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Create a context for a specific path and set the handler
        server.createContext("/", new MyHandler());

        // Start the server
        server.setExecutor(null); // Use the default executor
        server.start();

        logger.info("Started server");
        System.out.println("Server is running on port 8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            logger.info("{} {}", exchange.getRequestMethod(), exchange.getRequestURI());
            // handle the request
            try {
                byte[] targetArray = getBytes(exchange);
                exchange.getResponseHeaders().put("Content-Type", Collections.singletonList(exchange.getRequestURI().getPath().endsWith(".ico") ? "image/vnd.microsoft.icon" : "text/html"));
                exchange.sendResponseHeaders(200, targetArray.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(targetArray);
                }
            } catch (Exception e) {
                exchange.sendResponseHeaders(404, 0);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write("404 Not found".getBytes());
                }
            }
        }

        private static byte[] getBytes(HttpExchange exchange) throws IOException {
            byte[] targetArray = new byte[0];
            String fileName = exchange.getRequestURI().getPath().equals("/") ? "/Index.html" : exchange.getRequestURI().getPath();

            try (InputStream inputStream = MyHandler.class.getResourceAsStream("/content" + fileName)) {
                targetArray = new byte[inputStream.available()];
                inputStream.read(targetArray);
            }

            return targetArray;
        }
    }
}
