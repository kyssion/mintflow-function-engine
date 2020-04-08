package org.melkweg.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import org.melkweg.MelkwegTemplate;

import java.util.HashMap;

public class Test {

    @org.junit.jupiter.api.Test
    public void test1(){
        Vertx vertx = Vertx.vertx();
        MelkwegVertx melkwegVertx = MelkwegVertx.newBuilder(vertx).useMysql("").build(new HashMap<>());
        MelkwegTemplate melkwegTemplate = MelkwegTemplate.newBuilder().addInterface(melkwegVertx,"sdf").build();
    }

    @org.junit.jupiter.api.Test
    public void vertxTest(){
        Vertx vertx = Vertx.vertx();
        HttpServerOptions options = new HttpServerOptions().setMaxWebSocketFrameSize(1000000);

        HttpServer server = vertx.createHttpServer(options);
    }
}