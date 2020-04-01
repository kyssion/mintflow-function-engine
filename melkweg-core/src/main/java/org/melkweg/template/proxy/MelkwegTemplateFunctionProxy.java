package org.melkweg.template.proxy;

import org.melkweg.Melkweg;
import org.melkweg.annotation.MelkwegContextParam;
import org.melkweg.annotation.MelkwegNameSpace;
import org.melkweg.annotation.MelkwegParam;
import org.melkweg.annotation.MelkwegProcess;
import org.melkweg.param.ParamWrapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MelkwegTemplateFunctionProxy<T> implements InvocationHandler {
    private Melkweg melkweg;
    private String nameSpace;
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
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        MelkwegProcess melkwegProcess = method.getAnnotation(MelkwegProcess.class);
        String process ="";
        if(melkwegProcess!=null&&!"".equals(melkwegProcess.name())){
            process = melkwegProcess.name();
        }else{
            process = method.getName();
        }
        ParamWrapper paramWrapper = new ParamWrapper();
        for(Object object: objects){
            Class<?> item = object.getClass();
            MelkwegContextParam melkwegContextParam = item.getAnnotation(MelkwegContextParam.class);
            if(melkwegContextParam!=null){
                paramWrapper.setContextParam(melkwegContextParam.key(),object);
            }
            MelkwegParam melkwegParam = item.getAnnotation(MelkwegParam.class);
            if(melkwegParam!=null){
                paramWrapper.setParam(object.getClass(),object);
            }
        }
        paramWrapper =  this.melkweg.run(nameSpace,process,paramWrapper);
        return paramWrapper.getResult(method.getReturnType());
    }
}
