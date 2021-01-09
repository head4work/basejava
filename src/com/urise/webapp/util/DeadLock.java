package com.urise.webapp.util;

public class DeadLock {
    private static final Object LOCK = new Object();
    private static final Object LOCK1 = new Object();

    public static void main(String[] args) {
        new Thread(() -> lock(LOCK, LOCK1)).start();
        new Thread(() -> lock(LOCK1, LOCK)).start();
    }

    private static void lock(Object o1, Object o2) {
        synchronized (o1) {
            System.out.println("locked on Object " + o1);
            synchronized (o2) {
                System.out.println("locked on Object " + o1 + ", locking on Object " + o2);
            }
        }

    }

}
