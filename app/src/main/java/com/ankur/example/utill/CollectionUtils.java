package com.ankur.example.utill;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ankur Khandelwal on 7/26/2017.
 */
public class CollectionUtils {
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(Set<T> set) {
        return set == null || set.isEmpty();
    }
}
