package com.urise.webapp.util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static File file = new File(".");
    static int n = 1;

    public static void main(String[] args) throws IOException {
        printDir(file);
    }

    public static void printDir(File file) {

        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isFile()) {
                System.out.println(padLeft("File: ", n) + f.getName());
            } else if (f.isDirectory()) {
                System.out.println(padLeft("Dir: ", n) + f);
                n = n + 5;
                printDir(f);

            }
        }
        n = n - 5;
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

}


