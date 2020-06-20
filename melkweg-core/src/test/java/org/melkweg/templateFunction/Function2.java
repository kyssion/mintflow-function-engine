package org.melkweg.templateFunction;

import org.melkweg.annotation.MelkwegNameSpace;
import org.melkweg.annotation.MelkwegParam;
import org.melkweg.process.MelkwegProcess;
import org.melkweg.template.MelkwegTemplateFunction;

@MelkwegNameSpace(name = "x1")
public interface Function2 extends MelkwegTemplateFunction {
    @MelkwegProcess(name = "x2")
    String getName(@MelkwegParam String name);
}
