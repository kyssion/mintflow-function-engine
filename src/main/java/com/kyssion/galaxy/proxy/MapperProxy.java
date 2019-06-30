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
package com.kyssion.galaxy.proxy;

import com.kyssion.galaxy.annotation.ProcessMethod;
import com.kyssion.galaxy.exception.NoProcessIException;
import com.kyssion.galaxy.handle.StartHandler;
import com.kyssion.galaxy.param.ParamWrapper;
import com.kyssion.galaxy.process.Process;
import com.kyssion.galaxy.scheduler.HandlerScheduler;
import com.kyssion.galaxy.scheduler.Scheduler;

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
    private StringBuilder key;

    public MapperProxy(String nameSpace, Class<? extends Process> mapperClass, Map<String, StartHandler> startHanderMap) {
        this(nameSpace, mapperClass, startHanderMap, null);
    }

    public MapperProxy(String nameSpace, Class<? extends Process> mapperClass,
                       Map<String, StartHandler> startHanderMap, Scheduler scheduler) {
        this.startHanderMap = startHanderMap;
        this.scheduler = scheduler == null ? new HandlerScheduler() : scheduler;
        key = new StringBuilder(nameSpace);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        ProcessMethod process = method.getAnnotation(ProcessMethod.class);
        if (process != null) {
            key.append(".").append(process.id());
        } else {
            key.append(".").append(method.getName());
        }
        StartHandler startHandler = startHanderMap.get(key.toString());
        if (startHandler == null) {
            try {
                throw new NoProcessIException("this is no namespace Recording which name is :" + key.toString());
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
