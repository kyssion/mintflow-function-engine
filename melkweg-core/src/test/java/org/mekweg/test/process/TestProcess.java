package org.mekweg.test.process;

import org.mekweg.annotation.ProcessMethod;
import org.mekweg.annotation.ProcessNameSpace;
import org.mekweg.process.Process;

@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {

    @ProcessMethod(id="process1")
    Integer sayName(Integer name);

}
