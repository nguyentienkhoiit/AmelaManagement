package com.khoinguyen.amela.util;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;

public class UrlUtil {
    public static String getApplicationUrl(HttpServletRequest request) {
        String applicationUrl = request.getRequestURL().toString();
        return applicationUrl.replace(request.getServletPath(), "");
    }

    public static String getCurrentUrl(HttpServletRequest request) {
        return URI.create(request.getRequestURI()).getPath();
    }

    //http://localhost:8080/attendances
}
