package com.nbsunsoft.rtxproxy.util;

public class ArrayUtils {

    public static Integer[] parseStringArrayToIntegerArray(String[] strings) {
        Integer[] integers = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            integers[i] = new Integer(strings[i]);
        }
        return integers;
    }
}
