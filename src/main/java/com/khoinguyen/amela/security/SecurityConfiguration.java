package com.khoinguyen.amela.security;

import com.khoinguyen.amela.util.Constant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collection;

import static com.khoinguyen.amela.util.Constant.LIST_PERMIT_ALL;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfiguration {
    UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        a -> a.requestMatchers(LIST_PERMIT_ALL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        l -> l.loginPage("/login").usernameParameter("email")
                                .successHandler((request, response, authentication) -> {
                                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                                    if (authorities.stream().anyMatch(a -> a.getAuthority().equals(Constant.ADMIN_NAME))) {
                                        response.sendRedirect("/dashboard");
                                    } else {
                                        response.sendRedirect("/");
                                    }
                                })
                                .failureHandler((request, response, exception) -> {
                                    String email = request.getParameter("email");
                                    String redirectUrl = "/login?error=true&email=" + email;
                                    response.sendRedirect(redirectUrl);
                                })
                                .permitAll()
                )
                .logout(
                        l -> l.invalidateHttpSession(true).clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                );
        return http.build();
    }
}
