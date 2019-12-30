package org.mekweg.handle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ReorderHandler extends Handler {

    private List<Handler> childHandlrs = new ArrayList<>();

    public void addChilds(Handler... handlers) {
        childHandlrs.addAll(Arrays.asList(handlers));
    }

    public void addChilds(List<Handler> handlers) {
        this.childHandlrs.addAll(handlers);
    }

    @Override
    public Map<Class<?>, Object> handle(Map<Class<?>, Object> params) {
        return null;
    }
}
