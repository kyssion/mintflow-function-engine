package org.mintflow.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class RunTest {
    public void VertxRunTest(){
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        server.connectionHandler(httpConnection->{
            System.out.println(httpConnection.isSsl());
        });
    }
}
