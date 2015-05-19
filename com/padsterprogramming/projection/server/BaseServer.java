package com.padsterprogramming.projection.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/** Root HTTP handler. */
public class BaseServer implements HttpHandler {
  private final Charset CHARSET = Charset.forName("ISO-8859-1"); // Default web charset.

  // TODO - Flags -> StringMap parser.

  // TODO - Guicify.
  public static void main(String[] args) throws IOException {
    BaseServer baseHandler = new BaseServer();
    HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
    server.createContext("/", baseHandler);
    server.setExecutor(null); // Same-thread executor.
    server.start();
  }

  @Override public void handle(HttpExchange httpExchange) throws IOException {
    System.out.println("URI = " + httpExchange.getRequestURI());
    System.out.println("Method = " + httpExchange.getRequestMethod());

    Headers headers = httpExchange.getResponseHeaders();
    headers.set("Content-Type", "text/html"); // application/json for Json, application/javascript for JsonP

    OutputStream result = httpExchange.getResponseBody();
    String message = loadFile("com/padsterprogramming/projection/server/resources/template.jsont");

//    String message = "<html><head><title>Hi!</title></head><body><div>" + httpExchange.getRequestURI() + "</div></body></html>";
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
