package com.khoinguyen.amela.util;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {
    public static String getReferer(HttpServletRequest request, String HOST) {
        return request.getHeader("referer").replace(HOST, "");
    }
}
