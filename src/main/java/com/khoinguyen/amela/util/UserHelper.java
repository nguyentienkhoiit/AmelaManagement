package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHelper {
    HttpSession session;

    public User getUserLogin() {
        return (User) session.getAttribute("userLoggedIn");
    }

    public void setSecurityContext(
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password == null ? userDetails.getPassword() : password,
                        authorities == null ? userDetails.getAuthorities() : authorities
                )
        );
    }
}
