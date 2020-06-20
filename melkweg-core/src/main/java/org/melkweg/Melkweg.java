package org.melkweg;

import org.melkweg.async.result.AsyncResult;
import org.melkweg.async.param.AsyncParamWrapper;
import org.melkweg.builder.FnMapperBuilder;
import org.melkweg.exception.InitMelkwegException;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.FnHandler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;
import org.melkweg.scheduler.Scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Melkweg {

    public static class MelkwegBuilder{

        private Scheduler scheduler;

        private Map<String, Map<String, List<FnHandler>>> fnMapper;

        //Represents a collection of processors, used for logic consumption graphs and processor mapping
        private Map<String, FnHandler> handlerDataMap;

        public MelkwegBuilder(Map<String, FnHandler> handlerDataMap){
            this.handlerDataMap = handlerDataMap;
            this.fnMapper= new HashMap<>();
            this.scheduler = new FnEngineScheduler();
        }

        public MelkwegBuilder addScheduler(Scheduler scheduler){
            this.scheduler = scheduler;
            return this;
        }

        public MelkwegBuilder addFnMapper(String fnFilePath){
            if (this.handlerDataMap == null) {
                throw new InitMelkwegException("handlerDataMap没有初始化,请调用inithandlerDataMap初始化信息....");
            }
            try {
                this.fnMapper.putAll(FnMapperBuilder.build(handlerDataMap, fnFilePath));
            } catch (Exception e) {
                e.printStackTrace();
                throw new InitMelkwegException("初始化FnMapper失败....");
            }
            return this;
        }

        public Melkweg build(){
            Melkweg melkweg= new Melkweg();
            melkweg.scheduler = this.scheduler;
            melkweg.fnMapper = this.fnMapper;
            melkweg.handlerDataMap = this.handlerDataMap;
            return melkweg;
        }
    }

    private Scheduler scheduler;

    private Map<String, Map<String, List<FnHandler>>> fnMapper;

    //Represents a collection of processors, used for logic consumption graphs and processor mapping
    private Map<String, FnHandler> handlerDataMap;

    protected Melkweg() {
        super();
    }

    public ParamWrapper run(String namespace, String process, ParamWrapper paramWrapper) throws UserMelkwegException {
        List<FnHandler> processFnHandlerList = getMelkwegHandler(namespace,process);
        return this.scheduler.run(paramWrapper, processFnHandlerList);
    }

    public void AsyncRun(String namespace, String process , AsyncParamWrapper asyncParamWrapper, AsyncResult asyncResult){

    }

    public static MelkwegBuilder newBuilder(Map<String, FnHandler> handlerDataMap){
        return new MelkwegBuilder(handlerDataMap);
    }

    private List<FnHandler> getMelkwegHandler(String namespace, String process) throws UserMelkwegException {
        if (this.fnMapper == null) {
            throw new UserMelkwegException("fnMapper没有初始化,请使用initFnMapper方法初始化....");
        }
        Map<String, List<FnHandler>> namespaceItem = this.fnMapper.get(namespace);
        if (namespaceItem == null) {
            throw new UserMelkwegException("未发现指定的namespace信息....");
        }
        List<FnHandler> processFnHandlerList = namespaceItem.get(process);
        if (processFnHandlerList == null) {
            throw new UserMelkwegException("未发现指定的process流程信息....");
        }
        if (this.scheduler == null) {
            throw new UserMelkwegException("scheduler没有初始化,请使用initScheduler方法初始化....");
        }
        return processFnHandlerList;
    }
}
