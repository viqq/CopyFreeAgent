package com.free.agent.utils;

import com.google.common.collect.Sets;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by antonPC on 26.06.15.
 */
public final class HttpRequestUtil {
    public static Set<String> getParams(HttpServletRequest request, String name) {
        Enumeration enu = request.getParameterNames();
        Set<String> set = Sets.newHashSet();
        while (enu.hasMoreElements()) {
            String param = (String) enu.nextElement();
            if (param.equals(name)) {
                String[] values = request.getParameterValues(param);
                Collections.addAll(set, values);
                break;
            }
        }
        return set;
    }
}
