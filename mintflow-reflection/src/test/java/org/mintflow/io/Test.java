package org.mintflow.io;

import org.mintflow.reflection.io.ResourceFindUtil;

import java.io.IOException;
import java.util.List;

public class Test {
    @org.junit.Test
    public void resourceTest(){
        try {
            List<String> pathName = ResourceFindUtil.getFileListByPath("tttt/");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
