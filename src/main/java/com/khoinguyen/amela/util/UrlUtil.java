package com.khoinguyen.amela.util;

import jakarta.servlet.http.HttpServletRequest;

import static com.khoinguyen.amela.util.Constant.HOST;

public class UrlUtil {
    public static String getApplicationUrl(HttpServletRequest request) {
        String applicationUrl = request.getRequestURL().toString();
        return applicationUrl.replace(request.getServletPath(), "");
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader("referer").replace(HOST, "");
    }
}
