package com.urise.webapp.util;

public class DeadLock {
    private static final Object LOCK = new Object();
    private static final Object LOCK1 = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            lock(LOCK, LOCK1);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            lock(LOCK1, LOCK);
        });
        thread2.start();

    }

    private static void lock(Object o1, Object o2) {
        synchronized (o1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2) {
                double result = Math.sin(13.0);
                System.out.println(result);
            }
        }

    }

}
