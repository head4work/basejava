package com.urise.webapp.util;

public class LazySingleton {
    private static LazySingleton INSTANCE;

    private LazySingleton() {
    }

    private static class LazySingletonHolder {
        private final static LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getINSTANCE() {
        return LazySingletonHolder.INSTANCE;
    }

/*public static LazySingleton getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }*/
}
