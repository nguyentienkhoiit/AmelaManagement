package com.khoinguyen.amela.util;

import java.util.Collection;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.khoinguyen.amela.entity.User;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserHelper {
    HttpSession session;
    SessionRegistry sessionRegistry;

    public User getUserLogin() {
        return (User) session.getAttribute("userLoggedIn");
    }

    public void setSecurityContext(String password, Collection<? extends GrantedAuthority> authorities) {
        UserDetails userDetails = (UserDetails)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password == null ? userDetails.getPassword() : password,
                        authorities == null ? userDetails.getAuthorities() : authorities));
    }

    public void pushSessionExpired(User user) {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            if (principal instanceof UserDetails userDetails) {
                if (userDetails.getUsername().equals(user.getEmail())) {
                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
                    for (SessionInformation session : sessions) {
                        session.expireNow(); // Invalidate the session
                    }
                }
            }
        }
    }
}
