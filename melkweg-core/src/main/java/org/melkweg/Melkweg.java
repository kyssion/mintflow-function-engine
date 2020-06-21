package org.melkweg;

import org.melkweg.async.result.AsyncResult;
import org.melkweg.param.ParamWrapper;
import org.melkweg.handle.HandlerDataMap;
import org.melkweg.handle.async.AsyncFnHandler;
import org.melkweg.handle.sync.SyncFnHandler;
import org.melkweg.handle.util.MelkwegHandleMapBuilder;
import org.melkweg.scheduler.async.AsyncScheduler;
import org.melkweg.scheduler.async.FnAsyncEngineScheduler;
import org.melkweg.builder.FnMapperBuilder;
import org.melkweg.exception.InitMelkwegException;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.scheduler.sync.SyncFnEngineSyncScheduler;
import org.melkweg.scheduler.sync.SyncScheduler;

import java.util.List;

public class Melkweg {

    public static class MelkwegBuilder{

        private final HandlerDataMap handlerDataMap;

        //Represents a collection of processors, used for logic consumption graphs and processor mapping
        private final MelkwegHandleMapBuilder.Mapper handlerDataMapper;

        public MelkwegBuilder(MelkwegHandleMapBuilder.Mapper mapper){
            this.handlerDataMapper = mapper;
            this.handlerDataMap= new HandlerDataMap();
        }

        public MelkwegBuilder addFnMapper(String fnFilePath){
            try {
                this.handlerDataMap.addAll(FnMapperBuilder.build(handlerDataMapper, fnFilePath));
            } catch (Exception e) {
                e.printStackTrace();
                throw new InitMelkwegException("初始化FnMapper失败....");
            }
            return this;
        }

        public Melkweg build(){
            Melkweg melkweg= new Melkweg();
            melkweg.handlerDataMap = this.handlerDataMap;
            melkweg.handlerDataMapper = this.handlerDataMapper;
            return melkweg;
        }
    }

    private HandlerDataMap handlerDataMap;


    //Represents a collection of processors, used for logic consumption graphs and processor mapping
    private MelkwegHandleMapBuilder.Mapper handlerDataMapper;

    protected Melkweg() {
        super();
    }

    public ParamWrapper runSync(String namespace, String process, ParamWrapper paramWrapper, SyncScheduler syncScheduler)  {
        List<SyncFnHandler> processFnHandlerList = getHandlerSync(namespace,process);
        if(syncScheduler ==null){
            syncScheduler = new SyncFnEngineSyncScheduler();
        }
        return syncScheduler.run(paramWrapper, processFnHandlerList);
    }

    public void runAsync(String namespace, String process , ParamWrapper paramWrapper, AsyncResult asyncResult) {
        List<AsyncFnHandler> processFnHandlerList = getHandlerAsync(namespace,process);
        AsyncScheduler asyncScheduler = new FnAsyncEngineScheduler();
        asyncScheduler.asyncRun(paramWrapper,processFnHandlerList,asyncResult);
    }

    private List<SyncFnHandler> getHandlerSync(String namespace, String process){
        checkNamespaceInfo(namespace);
        HandlerDataMap.ProcessDataMap processDataMap = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        List<SyncFnHandler> syncFnHandlerList = processDataMap.getSyncFnDataMap().get(process);
        if (syncFnHandlerList == null) {
            throw new UserMelkwegException("未发现指定的process流程信息....");
        }
        return syncFnHandlerList;
    }

    private List<AsyncFnHandler> getHandlerAsync(String namespace,String process){
        checkNamespaceInfo(namespace);
        HandlerDataMap.ProcessDataMap processDataMap = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        List<AsyncFnHandler> asyncFnHandlerList = processDataMap.getAsyncFnDataMap().get(process);
        if (asyncFnHandlerList == null) {
            throw new UserMelkwegException("未发现指定的process流程信息....");
        }
        return asyncFnHandlerList;
    }

    private void checkNamespaceInfo(String namespace){
        if (this.handlerDataMap == null) {
            throw new UserMelkwegException("fnMapper没有初始化,请使用initFnMapper方法初始化....");
        }
        HandlerDataMap.ProcessDataMap namespaceItem = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        if (namespaceItem == null) {
            throw new UserMelkwegException("未发现指定的namespace信息....");
        }
    }

    public static MelkwegBuilder newBuilder(MelkwegHandleMapBuilder.Mapper handlerDataMap){
        return new MelkwegBuilder(handlerDataMap);
    }

}
