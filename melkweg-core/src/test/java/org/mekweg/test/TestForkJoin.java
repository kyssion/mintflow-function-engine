package org.mekweg.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class TestForkJoin {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(8);
        MyRecursiveAction myRecursiveAction = new MyRecursiveAction(new int[]{
                1,2,3,4,5,6,7,8,9
        },0,8);
        //阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭线程池
        forkJoinPool.shutdown();
    }
}

class MyRecursiveAction extends RecursiveAction{
    private int[] itemList;
    private int start;
    private int end;

    public MyRecursiveAction(int[] itemList,int start,int end){
        this.itemList = itemList;
        this.start = start;
        this.end = end;
    }
    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        if(start>end){
            return;
        }
        if(end+1-start>3){
            MyRecursiveAction recursiveAction = new MyRecursiveAction(itemList,start,start+2);
            MyRecursiveAction recursiveAction2 = new MyRecursiveAction(itemList,start+3,end);
            recursiveAction.fork();
            recursiveAction2.fork();
        }else{
            while(start<=end){
                System.out.print(itemList[start]+" ");
            }
            System.out.println();
        }
    }
}