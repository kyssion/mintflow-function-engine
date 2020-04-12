package org.melkweg.vertx;


import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLPool;
import org.melkweg.Melkweg;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.Handler;
import org.melkweg.param.ParamWrapper;

import java.util.Map;

public class MelkwegVertx extends Melkweg {

    private Vertx vertx;
    private MySQLPool mySQLPool;
    private Melkweg melkweg;
    private MelkwegVertx(){
        super();
    }
    public static class MelkwegVertxBuilder extends MelkwegBuilder{

        private Vertx vertx;

        private MySQLPool mySQLPool;

        public MelkwegVertxBuilder(Map<String, Handler> handlerMap,Vertx vertx){
            super(handlerMap);
            this.vertx = vertx;
        }

        public MelkwegVertxBuilder useMysql(String url){
            this.mySQLPool = MySQLPool.pool(vertx,url);
            return this;
        }

        public MelkwegVertx build(){
            MelkwegVertx melkwegVertx = (MelkwegVertx) super.build();
            melkwegVertx.vertx = vertx;
            melkwegVertx.mySQLPool = mySQLPool;
            return melkwegVertx;
        }
    }

    public static MelkwegVertxBuilder newBuilder(Map<String, Handler> handlerMap,Vertx vertx){
        return new MelkwegVertxBuilder(handlerMap,vertx);
    }

    public Vertx getVertx() {
        return vertx;
    }

    public MySQLPool getMySQLPool() {
        return mySQLPool;
    }


    @Override
    public ParamWrapper run(String namespace, String process, ParamWrapper paramWrapper) throws UserMelkwegException {
        return this.melkweg.run(namespace, process, paramWrapper);
    }
}
