package org.mekweg.test;

import java.util.ArrayList;
import java.util.List;

public class FinalizedTest {
    public static final FinalizedTest finalizedTest = new FinalizedTest();
    public static final List<TExecutorService> t = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Executors.create().execute();
                    }
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.gc();
                }
            }
        }).start();
    }

    public TFutureTask submit() {
        TExecutorService TExecutorService = Executors.create();
        TExecutorService.execute();
//        t.add(TExecutorService);
        return null;
    }
}