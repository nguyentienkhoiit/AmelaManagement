package com.khoinguyen.amela.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class InterceptorRequest implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) throws IOException {
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request method: {}", request.getMethod());
        log.info("Response status code: {}", response.getStatus());

        if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            response.sendRedirect("/notFound");
            return false;
        } else if (response.getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
            response.sendRedirect("/badRequest");
            return false;
        } else if (response.getStatus() == HttpServletResponse.SC_METHOD_NOT_ALLOWED) {
            response.sendRedirect("/methodNotAllowed");
            return false;
        } else if (response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            response.sendRedirect("/internalServerError");
            return false;
        } else if (
                response.getStatus() == HttpServletResponse.SC_FORBIDDEN
                        || response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED
        ) {
            response.sendRedirect("/forbidden");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable ModelAndView modelAndView
    ) {
        log.info("Response status code: {}", response.getStatus());
    }

}
