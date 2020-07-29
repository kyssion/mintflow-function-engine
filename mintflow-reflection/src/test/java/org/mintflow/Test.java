package org.mintflow;

import org.mintflow.reflection.MirrorObject;

public class Test {
    @org.junit.Test
    public void test() {
        MirrorObject mirrorObject = MirrorObject.forObject(new Item());
        String[] name = mirrorObject.getFiledMirrorObject().getGetterNames();
        for (String str : name) {
            System.out.println(str);
        }
    }
}

class Item {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
