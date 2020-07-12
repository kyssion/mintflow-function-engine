package org.mintFlow.vertxSupportExample;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class SampleVertx {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler((req)->{
            req.response().end("ok");
        });
        server.listen(8080,  res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
