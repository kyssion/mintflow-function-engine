package com.kyssion.galaxy;

import com.kyssion.galaxy.annotation.Processer;
import com.kyssion.galaxy.process.Process;

@Processer(id = "xxx")
public interface ProcessOne extends Process {
    String test(Object string);

    String test2(Object string);

    String test3(Object string);

    String test4(Object string);
}