package org.melkweg.vertx.scheduler;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLPool;
import org.melkweg.handle.Handler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;
import org.melkweg.vertx.MelkwegVertx;

import java.util.List;

public class MelkwegVertxScheduler extends FnEngineScheduler {

    private MelkwegVertx melkwegVertx;
    public MelkwegVertxScheduler(MelkwegVertx melkwegVertx){
        this.melkwegVertx = melkwegVertx;
    }

    @Override
    public ParamWrapper run(ParamWrapper paramWrapper, List<Handler> handlerList) {
        if(melkwegVertx.getMySQLPool()!=null){
            paramWrapper.setParam(MySQLPool.class,melkwegVertx.getMySQLPool());
        }
        if(melkwegVertx.getVertx()!=null){
            paramWrapper.setParam(Vertx.class,melkwegVertx.getVertx());
        }
        return super.run(paramWrapper,handlerList);
    }
}
