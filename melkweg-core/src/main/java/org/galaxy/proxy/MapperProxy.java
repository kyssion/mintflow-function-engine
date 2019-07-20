/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.galaxy.proxy;

import org.galaxy.annotation.ProcessMethod;
import org.galaxy.annotation.ProcessNameSpace;
import org.galaxy.exception.NoProcessIException;
import org.galaxy.handle.StartHandler;
import org.galaxy.param.ParamWrapper;
import org.galaxy.scheduler.HandlerScheduler;
import org.galaxy.scheduler.Scheduler;
import org.galaxy.process.Process;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 */
public class MapperProxy<T extends Process> implements InvocationHandler {

    private static final long serialVersionUID = -6424540398559729838L;

    private Map<String, StartHandler> startHanderMap;
    private Scheduler scheduler;
    Class<? extends Process> mapperClass;
    public MapperProxy(Class<? extends Process> mapperClass, Map<String, StartHandler> startHanderMap) {
        this(mapperClass, startHanderMap, null);
    }

    public MapperProxy(Class<? extends Process> mapperClass,
                       Map<String, StartHandler> startHanderMap, Scheduler scheduler) {
        this.startHanderMap = startHanderMap;
        this.scheduler = scheduler == null ? new HandlerScheduler() : scheduler;
        this.mapperClass=mapperClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        StringBuilder key = new StringBuilder();
        ProcessMethod process = method.getAnnotation(ProcessMethod.class);
        ProcessNameSpace nameSpace = this.mapperClass.getAnnotation(ProcessNameSpace.class);
        if (process != null&&nameSpace!=null) {
           key.append(nameSpace.id()).append(".").append(process.id());
        } else {
            key.append(this.mapperClass.getName()).append(".").append(method.getName());
        }
        StartHandler startHandler = startHanderMap.get(key.toString());
        if (startHandler == null) {
            try {
                throw new NoProcessIException("this is no namespace Recording which name is :" + key);
            } catch (NoProcessIException e) {
                e.printStackTrace();
                return null;
            }
        }
        ParamWrapper paramWrapper = new ParamWrapper();
        for (Object item : args) {
            paramWrapper.put(item);
        }
        paramWrapper = this.scheduler.run(paramWrapper, startHandler.getHandleList());
        return paramWrapper.get(method.getReturnType());
    }

}
