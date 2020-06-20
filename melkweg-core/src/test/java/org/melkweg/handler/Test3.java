package org.melkweg.handler;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.SampleFnHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "x3")
public class Test3 extends SampleFnHandler {
    public Test3(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        System.out.println(params.getContextParams().get("name"));
        System.out.println(params.getContextParams().get("passwd"));
        params.setParam(Object.class,"sdfsfsdf"+"x2"+"x3");
        return params;
    }
}
