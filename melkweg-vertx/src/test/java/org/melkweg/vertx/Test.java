package org.melkweg.vertx;

import io.vertx.core.Vertx;
import org.melkweg.MelkwegTemplate;

import java.util.HashMap;

public class Test {

    @org.junit.jupiter.api.Test
    public void test1(){
        Vertx vertx = Vertx.vertx();
        MelkwegVertx melkwegVertx = MelkwegVertx.newBuilder(vertx).build(new HashMap<>());
        MelkwegTemplate melkwegTemplate = MelkwegTemplate.newBuilder().addInterface(melkwegVertx,"sdf").build();
    }
}
