package org.melkweg;

import org.melkweg.builder.FnMapperBuilder;
import org.melkweg.exception.InitMelkwegError;
import org.melkweg.exception.UserMelkwegException;
import org.melkweg.handle.Handler;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.FnEngineScheduler;
import org.melkweg.scheduler.Scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Melkweg {

    private Scheduler scheduler;
    private Map<String, Map<String, List<Handler>>> fnMapper;

    //Represents a collection of processors, used for logic consumption graphs and processor mapping
    private Map<String, Handler> handlerDataMap;

    private Melkweg() {
        super();
    }

    public static Melkweg create(Map<String, Handler> handlerDataMap) {
        return create(handlerDataMap, new FnEngineScheduler());
    }

    public static Melkweg create(Map<String, Handler> handlerDataMap, Scheduler scheduler) {
        Melkweg melkweg = new Melkweg();
        melkweg.handlerDataMap = handlerDataMap;
        melkweg.scheduler = scheduler;
        melkweg.fnMapper = new HashMap<>();
        return melkweg;
    }

    public Melkweg addFnMapper(String fnFilePath) {
        if (this.handlerDataMap == null) {
            throw new InitMelkwegError("handlerDataMap没有初始化,请调用inithandlerDataMap初始化信息....");
        }
        try {
            this.fnMapper.putAll(FnMapperBuilder.build(handlerDataMap, fnFilePath));
        } catch (Exception e) {
            e.printStackTrace();
            throw new InitMelkwegError("初始化FnMapper失败....");
        }
        return this;
    }



    public ParamWrapper run(String namespace, String process, ParamWrapper paramWrapper) throws UserMelkwegException {
        if (this.fnMapper == null) {
            throw new UserMelkwegException("fnMapper没有初始化,请使用initFnMapper方法初始化....");
        }
        Map<String, List<Handler>> namespaceItem = this.fnMapper.get(namespace);
        if (namespaceItem == null) {
            throw new UserMelkwegException("未发现指定的namespace信息....");
        }
        List<Handler> processHandlerList = namespaceItem.get(process);
        if (processHandlerList == null) {
            throw new UserMelkwegException("未发现指定的process流程信息....");
        }
        if (this.scheduler == null) {
            throw new UserMelkwegException("scheduler没有初始化,请使用initScheduler方法初始化....");
        }
        return this.scheduler.run(paramWrapper, processHandlerList);
    }
}
