package org.mekweg;

import org.mekweg.builder.FnMapperBuilder;
import org.mekweg.exception.InitMekwegError;
import org.mekweg.exception.UserMekwegException;
import org.mekweg.handle.Handler;
import org.mekweg.param.ParamWrapper;
import org.mekweg.scheduler.FnEngineScheduler;
import org.mekweg.scheduler.Scheduler;

import java.util.List;
import java.util.Map;

public class Mekweg {

    private Scheduler scheduler;
    private Map<String, Map<String, List<Handler>>> fnMapper;
    private Map<String, Handler> handlerDataMap;

    public static Mekweg build() {
        return new Mekweg();
    }

    public Mekweg initScheduler() {
        this.scheduler = new FnEngineScheduler();
        return this;
    }

    public Mekweg inithandlerDataMap(Map<String, Handler> handlerDataMap) {
        this.handlerDataMap = handlerDataMap;
        return this;
    }

    public Mekweg initFnMapper(String fnFilePath) {
        if (this.handlerDataMap == null) {
            throw new InitMekwegError("handlerDataMap没有初始化,请调用inithandlerDataMap初始化信息....");
        }
        try {
            this.fnMapper = FnMapperBuilder.build(fnFilePath, handlerDataMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InitMekwegError("初始化FnMapper失败....");
        }
        return this;
    }

    public ParamWrapper run(String namespace, String process, ParamWrapper paramWrapper) throws UserMekwegException {
        if (this.fnMapper == null) {
            throw new UserMekwegException("fnMapper没有初始化,请使用initFnMapper方法初始化....");
        }
        Map<String, List<Handler>> namespaceItem = this.fnMapper.get(namespace);
        if (namespaceItem == null) {
            throw new UserMekwegException("未发现指定的namespace信息....");
        }
        List<Handler> processHandlerList = namespaceItem.get(process);
        if (processHandlerList == null) {
            throw new UserMekwegException("未发现指定的process流程信息....");
        }
        if (this.scheduler == null) {
            throw new UserMekwegException("scheduler没有初始化,请使用initScheduler方法初始化....");
        }
        return this.scheduler.run(paramWrapper, processHandlerList);
    }
}
