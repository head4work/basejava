package com.urise.webapp.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String path = "D:\\Basejava";
        File file = new File(path);
        List<String> rootPath = new ArrayList<>();

        getCurrentRootDirs(path, file, rootPath);
        getInnerRootDirs(rootPath);
        printContent(rootPath);
    }

    private static void getCurrentRootDirs(String path, File file, List<String> subpath) {
        for (String name : Objects.requireNonNull(file.list())) {
            if (new File(path + "\\" + name).isDirectory()) {
                subpath.add(path + "\\" + name);
            }
        }
    }

    private static void getInnerRootDirs(List<String> rootPath) {
        for (int k = 0; k < rootPath.size(); k++) {
            for (int i = 0; i < Objects.requireNonNull(new File(rootPath.get(k)).list()).length; i++) {
                List<String> list = Arrays.asList(Objects.requireNonNull(new File(rootPath.get(k)).list()));
                if (new File(rootPath.get(k) + "\\" + list.get(i)).isDirectory()) {
                    rootPath.add(rootPath.get(k) + "\\" + list.get(i));
                }
            }
        }
    }

    private static void printContent(List<String> subpath) {
        for (String root : subpath) {
            System.out.println(root);
            for (String name : Objects.requireNonNull(new File(root).list())) {
                System.out.println(name);
            }
        }
    }

}
