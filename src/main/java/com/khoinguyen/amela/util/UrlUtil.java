package com.khoinguyen.amela.util;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtil {
    public static String getApplicationUrl(HttpServletRequest request){
        String applicationUrl = request.getRequestURL().toString();
        return applicationUrl.replace(request.getServletPath(), "");
    }
}
