package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class StreamUtil {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{7, 6, 4, 9, 3, 3, 2}));
        System.out.println(oddOrEven(new ArrayList<>(Arrays.asList(1, 2, 1))));
    }

    private static int minValue(int[] values) {

        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        List<Integer> list;
        if (integers.stream().reduce(Integer::sum).get() % 2 == 0) {
            list = integers.stream().filter(integer -> integer % 2 != 0).collect(Collectors.toList());
        } else {
            list = integers.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        }
        return list;
    }

}
