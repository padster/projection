package com.padsterprogramming.projection.server;

import com.padsterprogramming.projection.model.StringMap;
import com.padsterprogramming.projection.model.Type;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/** Root HTTP handler. */
public abstract class BaseServer implements HttpHandler {
  private final Charset CHARSET = Charset.forName("ISO-8859-1"); // Default web charset.

  private final Map<String, StringMap<?>> storeHandlers = new HashMap<>();

  // TODO - Flags -> StringMap parser.

  // TODO - Guicify.
  public static <T extends BaseServer> void run(T baseServer, String[] args) throws IOException {
    baseServer.configureHandlers();
    HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
    server.createContext("/", baseServer);
    server.setExecutor(null); // Same-thread executor.
    server.start();
  }

  // Override this to add custom handling
  public abstract void configureHandlers();

  // Register handlers for a given URL
  // TODO: implement, with pattern matching for arg extraction

  // Register storage handlers with a given base.
  public void handleStore(String path, StringMap<?> store) {
    storeHandlers.put(path, store);
  }


  @Override public void handle(HttpExchange httpExchange) throws IOException {
    System.out.println("URI = " + httpExchange.getRequestURI().getPath());
    System.out.println("Method = " + httpExchange.getRequestMethod());

    String fullPath = httpExchange.getRequestURI().getPath();
    String method = httpExchange.getRequestMethod();

    Type[] response = {null};
    storeHandlers.forEach((path, store) -> {
      if (fullPath.startsWith(path)) {
        String remainder = fullPath.substring(path.length());
        if (remainder.startsWith("/")) { // HACK - should be a cleaner way,,,Or not needed at all.
          remainder = remainder.substring(1);
        }
        System.out.println("Handing store for " + remainder);
        response[0] = store.get(remainder);
      }
    });

    Headers headers = httpExchange.getResponseHeaders();
    headers.set("Content-Type", "text/plain"); // text/html for html, application/json for Json, application/javascript for JsonP

    OutputStream result = httpExchange.getResponseBody();
    String message = response[0].toString();

    httpExchange.sendResponseHeaders(200, message.length());
    result.write(message.getBytes(CHARSET)); // HACK - use fixed charset.
    result.close();
  }

  private String loadFile(String path) {
    InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);

    // HACK - stream from file.
    try(java.util.Scanner s = new java.util.Scanner(stream)) {
      if (s.useDelimiter("\\A").hasNext()) {
        return s.next();
      }
    }
    return "";
  }
}
