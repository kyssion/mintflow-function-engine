package org.mekweg.test;

public class Executors {
    /**
     * 模拟 Executors.createSingleExecutor
     * @return
     */
    public static TExecutorService create(){
        return new FinalizableDelegatedTExecutorService(new TThreadPoolExecutor());
    }

    static class FinalizableDelegatedTExecutorService extends DelegatedTExecutorService {

        FinalizableDelegatedTExecutorService(TExecutorService executor) {
            super(executor);
        }

        @Override
        protected void finalize() throws Throwable {
            super.shutdown();
        }
    }

    static class DelegatedTExecutorService extends TExecutorService {

        protected TExecutorService e;

        public DelegatedTExecutorService(TExecutorService executor) {
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
    }
}