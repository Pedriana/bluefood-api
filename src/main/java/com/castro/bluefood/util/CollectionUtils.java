package com.castro.bluefood.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> List<T> listOf(T... objs) {
        if (objs == null) {
            return Collections.emptyList();
        }

        //List<T> list = new ArrayList<>(objs.length);
        //for (T obj : objs) {
        //	list.add(obj);
        //}
        //return list;

        return Arrays.stream(objs).collect(Collectors.toList());
    }
}
