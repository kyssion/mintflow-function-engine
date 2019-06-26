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

import com.kyssion.galaxy.handle.header.StartHander;
import com.kyssion.galaxy.scheduler.HandlerScheduler;
import com.kyssion.galaxy.scheduler.Scheduler;
import org.mirror.reflection.Reflector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 */
public class MapperProxy<T> implements InvocationHandler {

    private static final long serialVersionUID = -6424540398559729838L;

    private Map<String, StartHander> startHanderMap;
    private Scheduler scheduler;

    public MapperProxy(Class<?> mapperClass, Map<String, StartHander> startHanderMap) {
        this(mapperClass, startHanderMap, null);
    }

    public MapperProxy(Class<?> mapperClass,
                       Map<String, StartHander> startHanderMap, Scheduler scheduler) {
        this.startHanderMap = startHanderMap;
        this.scheduler = scheduler == null ? new HandlerScheduler() : scheduler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        return null;
    }

}
