package com.oppo.seckilldemo.utils;

import org.springframework.lang.Nullable;

public class StringUtil {

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

}
