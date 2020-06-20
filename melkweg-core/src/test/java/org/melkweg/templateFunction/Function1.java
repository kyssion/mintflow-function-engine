package org.melkweg.templateFunction;

import org.melkweg.annotation.MelkwegContextParam;
import org.melkweg.annotation.MelkwegNameSpace;
import org.melkweg.annotation.MelkwegParam;
import org.melkweg.param.ParamWrapper;
import org.melkweg.process.MelkwegProcess;
import org.melkweg.template.MelkwegTemplateFunction;

@MelkwegNameSpace(name = "test_namespace")
public interface Function1 extends MelkwegTemplateFunction {
    @MelkwegProcess(name = "sys_test_process")
    ParamWrapper test(@MelkwegParam Integer num, @MelkwegParam String item,
                      @MelkwegContextParam(key = "condition_1") String condition1,
                      @MelkwegContextParam(key = "condition_2") String condition2,
                      @MelkwegContextParam(key = "condition_3") String condition3,
                      @MelkwegContextParam(key = "condition_4") String condition4,
                      @MelkwegContextParam(key = "show_start") boolean showStart,
                      @MelkwegContextParam(key = "show_end") boolean showEnd);
}
