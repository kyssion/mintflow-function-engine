package com.kyssion.galaxy.test.process;

import com.kyssion.galaxy.annotation.ProcessMethod;
import com.kyssion.galaxy.annotation.ProcessNameSpace;
import com.kyssion.galaxy.process.Process;

@ProcessNameSpace(id = "namespace1")
public interface TestProcess extends Process {

    @ProcessMethod(id="process1")
    Integer sayName(Integer name);

}
