package org.melkweg.template.proxy;

import org.melkweg.Melkweg;
import org.melkweg.annotation.*;
import org.melkweg.async.result.AsyncResult;
import org.melkweg.exception.HandleUseException;
import org.melkweg.param.ParamWrapper;
import org.melkweg.scheduler.sync.SyncFnEngineSyncScheduler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MelkwegTemplateFunctionProxy<T> implements InvocationHandler {

    private final Melkweg melkweg;
    private final String nameSpace;

    public MelkwegTemplateFunctionProxy(Class<T> itemClass, Melkweg melkweg){
        this.melkweg = melkweg;
        MelkwegNameSpace melkwegNameSpace = itemClass.getAnnotation(MelkwegNameSpace.class);
        if(melkwegNameSpace!=null&&!"".equals(melkwegNameSpace.name())){
            nameSpace = melkwegNameSpace.name();
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
            throw new HandleUseException("当前未发现，结果回调获取逻辑，method："+method.getName());
        }
        AsyncResult asyncResult = (AsyncResult) objects[lastOne];
        this.melkweg.runAsync(nameSpace,processName,paramWrapper,asyncResult);
        return null;
    }

    private Object runSync(Method method, Object[] objects) {
        String processName = getProcessName(method);
        ParamWrapper paramWrapper = getParamWrapper(method,objects);
        paramWrapper =  this.melkweg.runSync(nameSpace,processName,paramWrapper,new SyncFnEngineSyncScheduler());
        if(method.getReturnType()== ParamWrapper.class){
            return paramWrapper;
        }
        return paramWrapper.getResult(method.getReturnType());
    }


    private String getProcessName(Method method){
        MelkwegProcess melkwegProcess = method.getAnnotation(MelkwegProcess.class);
        if(melkwegProcess !=null&&!"".equals(melkwegProcess.name())){
            return  melkwegProcess.name();
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
                if(annotation instanceof  MelkwegParam){
                    paramWrapper.getParams().put(objects[a].getClass(),objects[a]);
                    break;
                }
                if(annotation instanceof MelkwegContextParam){
                    String key = ((MelkwegContextParam) annotation).key();
                    paramWrapper.getContextParams().put(key,objects[a]);
                    break;
                }
            }
        }
        return paramWrapper;
    }

}
