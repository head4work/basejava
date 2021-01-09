package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {


        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
                System.out.println(getState());
            }
        };
        thread0.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        }).start();
        System.out.println(thread0.getState());

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            });
            thread.start();
            threads.add(thread);

        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    private static void inc() {
        double res = Math.sin(13.0);

        synchronized (LOCK) {
            counter++;
        }


    }


}
