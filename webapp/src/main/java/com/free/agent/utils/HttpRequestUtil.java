package com.free.agent.utils;

import com.google.common.collect.Sets;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by antonPC on 26.06.15.
 */
public final class HttpRequestUtil {
    public static  Set<String> getParams(HttpServletRequest request,String name) {
        Enumeration enu = request.getParameterNames();
        Set<String> set = Sets.newHashSet();
        while (enu.hasMoreElements()) {
            String param = (String) enu.nextElement();
            String[] values = request.getParameterValues(param);

            for (int i = 0; i < values.length; ++i) {
                System.out.println(param + "[" + i + "] = " + values[i]);
                if (param.equals(name)) {
                    set.add( values[i]);
                }
            }
        }
        return set;
    }
}
