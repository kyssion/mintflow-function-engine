package org.mekweg;

public class JavaGCTestMain {
    @Override
    protected void finalize() {
        System.out.println(this + " was finalized!");
    }

    public static void main(String[] args) {
        JavaGCTestMain a = new JavaGCTestMain();
        System.out.println("Created " + a);
        for (int i = 0; i < 1_000_000_000; i++) {
            //制定一定的时间间隔触发gc
            if (i % 1_000_00 == 0) {
                System.gc();
            }
        }
        System.out.println("done.");
    }
}