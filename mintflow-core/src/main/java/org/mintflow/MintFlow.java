package org.mintflow;

import org.mintflow.async.result.AsyncResult;
import org.mintflow.handler.MintFlowHandlerMap;
import org.mintflow.param.ParamWrapper;
import org.mintflow.handler.HandlerDataMap;
import org.mintflow.handler.async.AsyncFnHandler;
import org.mintflow.handler.sync.SyncFnHandler;
import org.mintflow.scheduler.async.AsyncScheduler;
import org.mintflow.scheduler.async.FnAsyncEngineScheduler;
import org.mintflow.builder.FnMapperBuilder;
import org.mintflow.exception.InitMintFlowException;
import org.mintflow.exception.UseMintFlowException;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;
import org.mintflow.scheduler.sync.SyncScheduler;

import java.util.List;

public class MintFlow {

    public static class MintFlowBuilder{

        private final HandlerDataMap handlerDataMap;

        //Represents a collection of processors, used for logic consumption graphs and processor mapping
        private final MintFlowHandlerMap handlerDataMapper;

        public MintFlowBuilder(MintFlowHandlerMap mapper){
            this.handlerDataMapper = mapper;
            this.handlerDataMap= new HandlerDataMap();
        }

        public MintFlowBuilder addFnMapper(String fnFile){
            try {
                this.handlerDataMap.addAll(FnMapperBuilder.build(handlerDataMapper, fnFile));
            } catch (Exception e) {
                e.printStackTrace();
                throw new InitMintFlowException("初始化FnMapper失败....");
            }
            return this;
        }

        public MintFlowBuilder addFnMapperByDirectory(String directory){

        }

        public MintFlow build(){
            MintFlow MintFlow= new MintFlow();
            MintFlow.handlerDataMap = this.handlerDataMap;
            MintFlow.handlerDataMapper = this.handlerDataMapper;
            return MintFlow;
        }
    }

    private HandlerDataMap handlerDataMap;


    //Represents a collection of processors, used for logic consumption graphs and processor mapping
    private MintFlowHandlerMap handlerDataMapper;

    protected MintFlow() {
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
        AsyncScheduler asyncScheduler = new FnAsyncEngineScheduler(processFnHandlerList);
        asyncScheduler.next(paramWrapper,asyncResult);
    }

    private List<SyncFnHandler> getHandlerSync(String namespace, String process){
        checkNamespaceInfo(namespace);
        HandlerDataMap.ProcessDataMap processDataMap = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        List<SyncFnHandler> syncFnHandlerList = processDataMap.getSyncFnDataMap().get(process);
        if (syncFnHandlerList == null) {
            throw new UseMintFlowException("未发现指定的process流程信息....");
        }
        return syncFnHandlerList;
    }

    private List<AsyncFnHandler> getHandlerAsync(String namespace,String process){
        checkNamespaceInfo(namespace);
        HandlerDataMap.ProcessDataMap processDataMap = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        List<AsyncFnHandler> asyncFnHandlerList = processDataMap.getAsyncFnDataMap().get(process);
        if (asyncFnHandlerList == null) {
            throw new UseMintFlowException("未发现指定的process流程信息....");
        }
        return asyncFnHandlerList;
    }

    private void checkNamespaceInfo(String namespace){
        if (this.handlerDataMap == null) {
            throw new UseMintFlowException("fnMapper没有初始化,请使用initFnMapper方法初始化....");
        }
        HandlerDataMap.ProcessDataMap namespaceItem = this.handlerDataMap.getHandlerNamespaceMap().get(namespace);
        if (namespaceItem == null) {
            throw new UseMintFlowException("未发现指定的namespace信息....");
        }
    }

    public static MintFlowBuilder newBuilder(MintFlowHandlerMap handlerDataMap){
        return new MintFlowBuilder(handlerDataMap);
    }
}
