package com.kyssion.galaxy.process;

import com.kyssion.galaxy.mark.Type;

public interface Process {
    default Type type(){
        return Type.PROCESS;
    }
}
