package org.melkweg.templateFunction;

import org.melkweg.annotation.MelkwegContextParam;
import org.melkweg.annotation.MelkwegNameSpace;
import org.melkweg.process.MelkwegProcess;
import org.melkweg.template.MelkwegTemplateFunction;

@MelkwegNameSpace(name = "x1")
public interface Function1 extends MelkwegTemplateFunction {
    @MelkwegProcess(name = "x2")
    Object test(@MelkwegContextParam(key = "name") String name,
                @MelkwegContextParam(key = "passwd") String passwd);
}
