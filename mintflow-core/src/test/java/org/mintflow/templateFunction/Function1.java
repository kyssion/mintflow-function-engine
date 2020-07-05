package org.mintflow.templateFunction;

import org.mintflow.annotation.*;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.param.ParamWrapper;
import org.mintflow.template.MintFlowTemplateFunction;

@MintFlowNameSpace(name = "test_namespace")
public interface Function1 extends MintFlowTemplateFunction {
    @MintFlowProcess(name = "sync_test_process")
    ParamWrapper test(@MintFlowParam Integer num, @MintFlowParam String item,
                      @MintFlowContextParam(key = "condition_1") String condition1,
                      @MintFlowContextParam(key = "condition_2") String condition2,
                      @MintFlowContextParam(key = "condition_3") String condition3,
                      @MintFlowContextParam(key = "condition_4") String condition4,
                      @MintFlowContextParam(key = "show_start") boolean showStart,
                      @MintFlowContextParam(key = "show_end") boolean showEnd);
    @AsyncSupport
    @MintFlowProcess(name="async_test_process")
    void test(@MintFlowParam Integer num, @MintFlowParam String item,
              @MintFlowContextParam(key = "condition_1") String condition1,
              @MintFlowContextParam(key = "condition_2") String condition2,
              @MintFlowContextParam(key = "condition_3") String condition3,
              @MintFlowContextParam(key = "condition_4") String condition4,
              @MintFlowContextParam(key = "show_start") boolean showStart,
              @MintFlowContextParam(key = "show_end") boolean showEnd, AsyncResult asyncResult);
}
