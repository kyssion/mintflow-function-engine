package org.mintflow.vertx.http.controller;

import org.junit.Test;
import org.mintflow.vertx.http.controller.finder.FinderItem;
import org.mintflow.vertx.http.controller.finder.MintFlowControllerFinder;

import java.util.List;

public class ControllerFinderTest {

    @Test
    public void finderTest(){
        List<FinderItem> list = MintFlowControllerFinder.find("org.mintflow.vertx.http.controller.controllerInterface");
        System.out.println(list.size());
    }
}
