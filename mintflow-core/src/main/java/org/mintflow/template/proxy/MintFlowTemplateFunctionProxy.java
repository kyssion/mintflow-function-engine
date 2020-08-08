package org.mintflow.template.proxy;

import org.mintflow.MintFlow;
import org.mintflow.annotation.*;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.exception.HandlerUseException;
import org.mintflow.param.ParamWrapper;
import org.mintflow.scheduler.sync.SyncFnEngineSyncScheduler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MintFlowTemplateFunctionProxy<T> implements InvocationHandler {

    private final MintFlow MintFlow;
    private final String nameSpace;

    public MintFlowTemplateFunctionProxy(Class<T> itemClass, MintFlow MintFlow){
        this.MintFlow = MintFlow;
        MintFlowNameSpace MintFlowNameSpace = itemClass.getAnnotation(MintFlowNameSpace.class);
        if(MintFlowNameSpace!=null&&!"".equals(MintFlowNameSpace.name())){
            nameSpace = MintFlowNameSpace.name();
        }else{
            nameSpace = itemClass.getName();
        }
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects){
        AsyncSupport asyncSupport = method.getAnnotation(AsyncSupport.class);
        if(asyncSupport!=null){
            return runAsync(method,objects);
        }else{
            return runSync(method,objects);
        }
    }

    private Object runAsync(Method method, Object[] objects) {
        String processName = getProcessName(method);
        ParamWrapper paramWrapper = getParamWrapper(method,objects);
        int lastOne = objects.length-1;
        if(objects.length==0||!(objects[lastOne] instanceof AsyncResult)){
            throw new HandlerUseException("当前未发现，结果回调获取逻辑，method："+method.getName());
        }
        AsyncResult asyncResult = (AsyncResult) objects[lastOne];
        this.MintFlow.runAsync(nameSpace,processName,paramWrapper,asyncResult);
        return null;
    }

    private Object runSync(Method method, Object[] objects) {
        String processName = getProcessName(method);
        ParamWrapper paramWrapper = getParamWrapper(method,objects);
        paramWrapper =  this.MintFlow.runSync(nameSpace,processName,paramWrapper,new SyncFnEngineSyncScheduler());
        if(method.getReturnType()== ParamWrapper.class){
            return paramWrapper;
        }
        return paramWrapper.getResult(method.getReturnType());
    }


    private String getProcessName(Method method){
        MintFlowProcess MintFlowProcess = method.getAnnotation(MintFlowProcess.class);
        if(MintFlowProcess !=null&&!"".equals(MintFlowProcess.name())){
            return  MintFlowProcess.name();
        }else{
            return method.getName();
        }
    }

    private ParamWrapper getParamWrapper(Method method,Object[] objects){
        ParamWrapper paramWrapper = new ParamWrapper();
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int a=0;a<objects.length;a++){
            Annotation[] argAnnotion = annotations[a];
            for(Annotation annotation: argAnnotion){
                if(annotation instanceof  MintFlowParam){
                    paramWrapper.getParams().put(objects[a].getClass(),objects[a]);
                    break;
                }
                if(annotation instanceof MintFlowContextParam){
                    String key = ((MintFlowContextParam) annotation).key();
                    paramWrapper.getContextParams().put(key,objects[a]);
                    break;
                }
            }
        }
        return paramWrapper;
    }

}
