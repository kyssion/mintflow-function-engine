package org.mintflow.sql.base;

import java.util.List;

public class SqlTestBase {
    public Object[] createArrays(List<Object> list) {
        Object[] objects = new Object[list.size()];
        for (int a = 0; a < objects.length; a++) {
            objects[a] = list.get(a);
        }
        return objects;
    }

}
