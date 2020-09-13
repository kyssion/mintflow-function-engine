package org.mintflow.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class UserServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
    }
}
