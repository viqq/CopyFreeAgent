package com.free.agent.utils;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 16.06.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DaoUtils {

    private static final Logger LOGGER = Logger.getLogger(DaoUtils.class);

    public static <T> T getSingleResult(List<T> list) {
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        LOGGER.error("Table contains many unique elements");
        throw new RuntimeException("Table contains many unique elements");
    }

    public static <T> Set<T> getResultSet(List<T> list) {
        return Sets.newHashSet(list);
    }
}
