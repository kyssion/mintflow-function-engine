package org.mintFlow.vertxSupportExample;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.vertx.http.router.HttpRouter;

public class MintFlowVertxSupportExample {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                "org.mintFlow.vertxSupportExample.handle"
        );
        MintFlow mintFlow = MintFlow.newBuilder(dataMapper).addFnMapper("async/async_complex_test.fn").build();

        HttpRouter httpRouter = HttpRouter.router(mintFlow)
                .addRouter("org.mintFlow.vertxSupportExample.controller.controllerInterface");

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
