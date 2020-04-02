package org.melkweg.handler;

import org.melkweg.annotation.MelkwegHander;
import org.melkweg.handle.SampleHandler;
import org.melkweg.param.ParamWrapper;

@MelkwegHander(name = "x2")
public class Test2 extends SampleHandler {
    public Test2(String name) {
        super(name);
    }

    @Override
    public ParamWrapper handle(ParamWrapper params) {
        System.out.println(params.getContextParams().get("name"));
        System.out.println(params.getContextParams().get("passwd"));
        params.setParam(Object.class,"sdfsfsdf"+"x2");
        return params;
    }
}
