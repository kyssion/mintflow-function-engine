package org.melkweg.vertx;


import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLPool;
import org.melkweg.Melkweg;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.Handler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.vertx.scheduler.MelkwegVertxScheduler;

import java.util.Map;

public class MelkwegVertx extends Melkweg {

    private Vertx vertx;
    private MySQLPool mySQLPool;
    private Melkweg melkweg;
    private MelkwegVertx(){
        super();
    }
    public static class MelkwegVertxBuilder{

        private Vertx vertx;

        private MySQLPool mySQLPool;

        public MelkwegVertxBuilder(Vertx vertx){
            this.vertx = vertx;
        }

        public MelkwegVertxBuilder useMysql(String url){
            this.mySQLPool = MySQLPool.pool(vertx,url);
            return this;
        }

        public MelkwegVertx build(Map<String, Handler> handlerMap){
            MelkwegVertx melkwegVertx = new MelkwegVertx();
            melkwegVertx.vertx = vertx;
            melkwegVertx.mySQLPool = mySQLPool;
            MelkwegVertxScheduler scheduler = new MelkwegVertxScheduler(melkwegVertx);
            melkwegVertx.melkweg  = Melkweg.create(handlerMap,scheduler);
            return melkwegVertx;
        }
    }

    public static MelkwegVertxBuilder newBuilder(Vertx vertx){
        return new MelkwegVertxBuilder(vertx);
    }

    public Vertx getVertx() {
        return vertx;
    }

    public MySQLPool getMySQLPool() {
        return mySQLPool;
    }


    @Override
    public Melkweg addFnMapper(String fnFilePath) {
        return this.melkweg.addFnMapper(fnFilePath);
    }

    @Override
    public ParamWrapper run(String namespace, String process, ParamWrapper paramWrapper) throws UserMelkwegException {
        return this.melkweg.run(namespace, process, paramWrapper);
    }
}
