package org.mintflow.templateFunction;

import org.mintflow.annotation.*;
import org.mintflow.async.result.AsyncResult;
import org.mintflow.param.ParamWrapper;
import org.mintflow.process.ProcessType;
import org.mintflow.template.MintFlowTemplateFunction;

import static org.mintflow.handler.async.sample.AsyncCycleSampleHandler.ASYNC_CYCLE_STR;
import static org.mintflow.handler.async.sample.AsyncReorderSampleHandler.ASYNC_REORDER_STR;
import static org.mintflow.handler.sync.simple.CycleSampleHandler.SYNC_CYCLE_STR;
import static org.mintflow.handler.sync.simple.ReorderSampleHandler.SYNC_REORDER_STR;

@MintFlowNameSpace(name = "test_namespace")
public interface Function1 extends MintFlowTemplateFunction {

    @MintFlowProcess(name = "sync_test_process",type = ProcessType.SYNC)
    ParamWrapper test(@MintFlowParam Integer num, @ContextParam(key=SYNC_CYCLE_STR) String itemCycle,
                      @ContextParam(key=SYNC_REORDER_STR) String itemReorder,
                      @ContextParam(key = "condition_1") String condition1,
                      @ContextParam(key = "condition_2") String condition2,
                      @ContextParam(key = "condition_3") String condition3,
                      @ContextParam(key = "condition_4") String condition4,
                      @ContextParam(key = "show_start") boolean showStart,
                      @ContextParam(key = "show_end") boolean showEnd);

    @MintFlowProcess(name="async_test_process",type = ProcessType.ASYNC)
    void test(@MintFlowParam Integer num, @ContextParam(key= ASYNC_CYCLE_STR) String itemCycle,
              @ContextParam(key= ASYNC_REORDER_STR) String itemReorder,
              @ContextParam(key = "condition_1") String condition1,
              @ContextParam(key = "condition_2") String condition2,
              @ContextParam(key = "condition_3") String condition3,
              @ContextParam(key = "condition_4") String condition4,
              @ContextParam(key = "show_start") boolean showStart,
              @ContextParam(key = "show_end") boolean showEnd, AsyncResult asyncResult);
}
