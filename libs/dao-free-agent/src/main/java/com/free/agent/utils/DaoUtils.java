package com.free.agent.utils;

import java.util.List;

/**
 * Created by antonPC on 16.06.15.
 */
public class DaoUtils {
    public static <T> T getSingleResult(List<T> list) {
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }
}
