package org.mekweg.test;

import java.util.concurrent.atomic.AtomicBoolean;

public class TThreadPoolExecutor extends TExecutorService {

    /**
     * 线程池状态，false：未关闭，true 已关闭
     */
    private AtomicBoolean ctl = new AtomicBoolean(false);

    @Override
    public void execute() {
        // 启动一个新线程，模拟 ThreadPoolExecutor.execute
        new Thread(new Runnable() {
            @Override
            public void run() {}
        }).start();
        // 模拟 ThreadPoolExecutor，启动新建线程后，循环检查线程池状态，验证是否会在 finalize 中 shutdown
        // 如果线程池被提前 shutdown，则抛出异常
        for (int i = 0; i < 1_000_000; i++) {
            if(ctl.get()){
                System.out.println(FinalizedTest.finalizedTest);
                throw new RuntimeException("reject!!!["+ctl.get()+"]");
            }
        }
    }

    @Override
    public void shutdown() {
        ctl.compareAndSet(false,true);
    }
}