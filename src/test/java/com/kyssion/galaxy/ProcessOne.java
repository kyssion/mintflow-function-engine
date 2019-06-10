package com.kyssion.galaxy;

import com.kyssion.galaxy.annotation.ProcessId;

@ProcessId("xxx")
public interface ProcessOne {
    String test(Object string);

    String test2(Object string);

    String test3(Object string);

    String test4(Object string);
}