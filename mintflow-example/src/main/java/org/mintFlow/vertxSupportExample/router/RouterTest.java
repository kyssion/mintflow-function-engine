package org.mintFlow.vertxSupportExample.router;


import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.handler.util.MintFlowHandlerMapFinder;
import org.mintflow.vertx.http.router.HttpRouter;

public class RouterTest {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        MintFlowHandlerMap dataMapper = MintFlowHandlerMapFinder.findHandlerDataMap(
                "org.mintflow.vertx.http.handle"
        );
        HttpRouter httpRouter = HttpRouter.router(MintFlow.newBuilder(dataMapper).addFnMapper("async/httpControllerProcess.fn").build())
                .addRouter("org.mintflow.vertx.http.controller.controllerInterface");
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
