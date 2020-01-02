package org.mekweg;


import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadBugTest {
    public static void main(String[] args) {
        ThreadBugTest threadBugTest = new ThreadBugTest();
        for (int i = 0; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        threadBugTest.run();
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

    public void run(){
        ExecutorWrapper.create().execute();
    }
    //实际运行的类
    static class Executor{
        private AtomicBoolean ctl = new AtomicBoolean(false);
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
                    throw new RuntimeException("reject!!!["+ctl.get()+"]");
                }
            }
        }

        public void shutdown() {
            ctl.compareAndSet(false,true);
        }
    }

    static class ExecutorWrapper {

        public static Executor create(){
            return new FinalizableDelegatedTExecutorService(new Executor());
        }

        static class FinalizableDelegatedTExecutorService extends Executor {
            private Executor e;
            FinalizableDelegatedTExecutorService(Executor executor) {
                this.e = executor;
            }
            @Override
            public void execute() {
                e.execute();
            }

            @Override
            public void shutdown() {
                e.shutdown();
            }

            @Override
            protected void finalize() throws Throwable {
                this.shutdown();
            }
        }
    }
}
