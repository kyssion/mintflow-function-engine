package org.mekweg.handle;

import java.util.Map;

public abstract class Handler implements Cloneable{
    private HandleType type;

    public HandleType getType() {
        return type;
    }

    public void setType(HandleType type) {
        this.type = type;
    }

    public abstract Map<Class<?>,Object> handle(Map<Class<?>,Object> params);
}
