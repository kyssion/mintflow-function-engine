package org.melkweg.handle;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolsFnHandle extends FnHandler{

    private List<FnHandler> childs = new ArrayList<>();

    public List<FnHandler> getChilds() {
        return childs;
    }

    public void setChilds(List<FnHandler> childs) {
        this.childs = childs;
    }

    public void addChilds(List<FnHandler> childs) {
        this.childs.addAll(childs);
    }

    protected ToolsFnHandle(String name) {
        super(name);
    }

    protected ToolsFnHandle(String name, HandleType handleType) {
        super(name, handleType);
    }

    @Override
    public ToolsFnHandle clone() throws CloneNotSupportedException {
        ToolsFnHandle toolsFnHandle = (ToolsFnHandle) super.clone();
        toolsFnHandle.childs = new ArrayList<>();
        return toolsFnHandle;
    }
}
