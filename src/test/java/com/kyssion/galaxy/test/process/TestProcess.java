package com.kyssion.galaxy.test.process;

import com.kyssion.galaxy.annotation.Processer;
import com.kyssion.galaxy.process.Process;

@Processer(id = "namespace1")
public interface TestProcess extends Process {
    String sayName(String name);
}
