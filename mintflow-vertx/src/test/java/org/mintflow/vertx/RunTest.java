package org.mintflow.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;
import io.vertx.core.http.HttpServer;
import org.mintflow.MintFlow;
import org.mintflow.vertx.http.Router;

public class RunTest {
    public static void main(String[] args) {
        new RunTest().VertxRunTest();
    }
    public void VertxRunTest(){
        Vertx vertx = Vertx.vertx();
        HttpServer server = vertx.createHttpServer();
        //创建一个新的连接的时候将会调用这个回调
        server.connectionHandler(httpConnection->{
            System.out.println("link success");
            System.out.println(httpConnection.isSsl());
        });
        server.exceptionHandler(throwable -> {
            System.err.println("error");
            throwable.printStackTrace();
        });
        server.requestHandler(httpServerRequest -> {
            Buffer buffer = new BufferImpl();
            httpServerRequest.handler(buf->{
                System.out.println("handler : "+buf.toString());
                buffer.appendBuffer(buf);
            });
            httpServerRequest.endHandler((v)->{
                System.out.println("end:"+buffer.toString());
                httpServerRequest.response().end(buffer.toString());
            });
//            httpServerRequest.bodyHandler(buf->{
//                System.out.println("end : "+buf.toString());
//                httpServerRequest.response().end(buf.toString());
//            });
//            httpServerRequest.exceptionHandler(throwable -> {
//                System.out.println("exception_handler");
//                throwable.printStackTrace();
//            });
        });

        server.requestHandler(new Router(MintFlow.newBuilder(null).build()));

        server.listen(8080,  res -> {
            if (res.succeeded()) {
                System.out.println("Server is now listening!");
            } else {
                System.out.println("Failed to bind!");
            }
        });
    }
}
