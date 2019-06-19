package com.kyssion.galaxy.script.node;

import com.kyssion.galaxy.script.type.HandleTypeEnum;

public abstract class Node {
    private String handleKey;

    public String getHandleKey() {
        return handleKey;
    }

    public void setHandleKey(String handleKey) {
        this.handleKey = handleKey;
    }

    abstract public HandleTypeEnum getType();
}
