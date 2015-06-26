package com.free.agent.utils;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 16.06.15.
 */
public final class DaoUtils {
    public static <T> T getSingleResult(List<T> list) {
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        throw new RuntimeException("Table contains many unique elements");
    }

    public static <T> Set<T> getResultSet(List<T> list) {
        return Sets.newHashSet(list);
    }
}
