package org.mintflow.simple;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.vertx.http.router.HttpRouter;

public class Example {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();

        MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                "org.mintflow.simple.handle"
        );

        HttpRouter httpRouter = HttpRouter.router(MintFlow.newBuilder(dataMapper).
                addFnMapper("async/test_async.fn").build())
                .addRouter("org.mintflow.simple.controller");

        server.requestHandler(httpRouter);



        server.listen(8080,  res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
