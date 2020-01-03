package org.mekweg.test;

public class TestMain extends Thread{
    private static boolean flag = false;
    public static void main(String[] args) throws InterruptedException {
        new TestMain().start();
        Thread.sleep(100);
        flag=true;
    }

    public void run(){
        final int a = 123;
        while(!flag){

        }
        System.out.println("end");
    }
}
