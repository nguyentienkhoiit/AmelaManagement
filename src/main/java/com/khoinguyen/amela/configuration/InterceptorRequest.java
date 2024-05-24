package com.khoinguyen.amela.configuration;

import com.khoinguyen.amela.entity.enums.ErrorResponse;
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
        log.info("Request URI: {}, Request method: {}, Response status code: {}",
                request.getRequestURI(), request.getMethod(), response.getStatus());
        for (ErrorResponse resp : ErrorResponse.values()) {
            if (response.getStatus() == resp.getErrorCode()) {
                response.sendRedirect(resp.getPath());
                return false;
            }
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
    }

}
