package org.melkweg.handle;

import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolsConditonHandlerWrapper extends FnHandler {
    private List<ToolsFnHandle> toolsFnHandles = new ArrayList<>();

    protected ToolsConditonHandlerWrapper(String name) {
        super(name);
    }

    protected ToolsConditonHandlerWrapper(String name, HandleType handleType) {
        super(name, handleType);
    }

    public void addConditionHandle(ToolsFnHandle toolsFnHandle){
        this.toolsFnHandles.add(toolsFnHandle);
    }
}
