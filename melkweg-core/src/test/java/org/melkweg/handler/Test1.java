package org.melkweg.handler;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.SampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "x1")
public class Test1 extends SampleFnHandler {
    public Test1(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        System.out.println(params.getContextParams().get("name"));
        System.out.println(params.getContextParams().get("passwd"));
        params.setParam(Object.class,"sdfsfsdf");
        return params;
    }
}
