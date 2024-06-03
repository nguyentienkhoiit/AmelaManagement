package com.khoinguyen.amela.security.custom;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.security.CustomUserDetails;
import com.khoinguyen.amela.util.Constant;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            User userLoggedIn = ((CustomUserDetails) principal).getUser();
            session.setAttribute("userLoggedIn", userLoggedIn);

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Constant.ADMIN_NAME))) {
                response.sendRedirect("/dashboard");
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Constant.USER_NAME))) {
                response.sendRedirect("/");
            }
        } else response.sendRedirect("/forbidden");
    }
}
