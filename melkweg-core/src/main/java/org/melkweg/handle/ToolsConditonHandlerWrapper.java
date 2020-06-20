package org.melkweg.handle;

import org.melkweg.handle.sync.SyncConditionFncHandlerWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolsConditonHandlerWrapper extends FnHandler {
    protected List<ToolsFnHandle> toolsFnHandles = new ArrayList<>();

    public List<ToolsFnHandle> getToolsFnHandles() {
        return toolsFnHandles;
    }

    public void setToolsFnHandles(List<ToolsFnHandle> toolsFnHandles) {
        this.toolsFnHandles = toolsFnHandles;
    }

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
