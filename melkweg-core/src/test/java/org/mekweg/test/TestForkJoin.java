package org.mekweg.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        MyRecursiveTask myRecursiveAction = new MyRecursiveTask(new int[] {
                1, 2, 3, 4, 5, 6, 7, 8, 9
        }, 0, 8);
        Future<Integer> item = forkJoinPool.submit(myRecursiveAction);
        System.out.println(item.get());
    }
}

class MyRecursiveTask extends RecursiveTask<Integer> {

    private int[] itemList;
    private int start;
    private int end;

    public MyRecursiveTask(int[] itemList, int start, int end) {
        this.itemList = itemList;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if(start==end){
            return itemList[start];
        }
        MyRecursiveTask left = new MyRecursiveTask(itemList,start,(start+end)/2);
        MyRecursiveTask right = new MyRecursiveTask(itemList,(start+end)/2+1,end);
        invokeAll(left,right);
        return left.join()+right.join();
    }
}

class MyRecursiveAction extends RecursiveAction {
    private int[] itemList;
    private int start;
    private int end;

    public MyRecursiveAction(int[] itemList, int start, int end) {
        this.itemList = itemList;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if (start > end) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (end + 1 - start > 3) {
            MyRecursiveAction recursiveAction = new MyRecursiveAction(itemList, start, start + 2);
            MyRecursiveAction recursiveAction2 = new MyRecursiveAction(itemList, start + 3, end);
            recursiveAction.fork();
            System.out.println("one end");
            recursiveAction2.fork();
            System.out.println("two end");
        } else {
            while (start <= end) {
                System.out.print(itemList[start] + " ");
                start++;
            }
            System.out.println();
        }
    }
}