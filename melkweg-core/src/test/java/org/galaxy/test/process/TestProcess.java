package org.galaxy.test.process;

import org.galaxy.annotation.ProcessMethod;
import org.galaxy.annotation.ProcessNameSpace;
import org.galaxy.process.Process;

@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {

    @ProcessMethod(id="process1")
    Integer sayName(Integer name);

}
