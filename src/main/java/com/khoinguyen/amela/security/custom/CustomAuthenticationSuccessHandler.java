package com.khoinguyen.amela.security.custom;

import com.khoinguyen.amela.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Constant.ADMIN_NAME))) {
            response.sendRedirect("/dashboard");
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Constant.USER_NAME))) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/forbidden");
        }
    }
}
