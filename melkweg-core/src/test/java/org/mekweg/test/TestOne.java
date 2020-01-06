package org.mekweg.test;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class TestOne {
    public static void main(String[] args) {

    }
}

class TestCase extends RecursiveAction{

    @Override
    protected void compute() {
        System.out.println("this is one");
    }
}


class TestTask extends RecursiveTask<Long> {

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Long compute() {
        return System.currentTimeMillis();
    }
}

