package org.mekweg;

import java.util.concurrent.Semaphore;

public class JavaGCTestMain {
    @Override
    protected void finalize() {
        System.out.println(this + " was finalized!");
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(12);
    }
}