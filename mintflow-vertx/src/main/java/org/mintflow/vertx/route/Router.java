package org.mintflow.vertx.route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import org.mintflow.MintFlow;

public class Router implements Handler<HttpServerRequest> {
    private final MintFlow mintFlow;

    public Router(MintFlow mintFlow){
        this.mintFlow = mintFlow;
    }

    @Override
    public void handle(HttpServerRequest event) {

    }
}
