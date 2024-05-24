package com.khoinguyen.amela.security;

import com.khoinguyen.amela.configuration.InterceptorRequest;
import com.khoinguyen.amela.security.custom.CustomAuthenticationFailureHandler;
import com.khoinguyen.amela.security.custom.CustomAuthenticationSuccessHandler;
import com.khoinguyen.amela.security.custom.CustomSessionExpiredStrategy;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.khoinguyen.amela.util.Constant.LIST_PERMIT_ALL;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfiguration implements WebMvcConfigurer {
    UserDetailsService userDetailsService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorRequest());
    }

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
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new CustomSessionExpiredStrategy();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s-> s
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(1)
                        .sessionRegistry(sessionRegistry())
                        .expiredSessionStrategy(sessionInformationExpiredStrategy()))
                .authorizeHttpRequests(
                        a -> a.requestMatchers(LIST_PERMIT_ALL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        l -> l.loginPage("/login").usernameParameter("email")
                                .successHandler(customAuthenticationSuccessHandler())
                                .failureHandler(customAuthenticationFailureHandler())
                                .permitAll()
                )
                .logout(
                        l -> l.invalidateHttpSession(true).clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login")
                )
                .oauth2Login(
                        l -> l.loginPage("/login")
                                .defaultSuccessUrl("/oauth2", true)
                )
                .exceptionHandling(e -> e.accessDeniedPage("/forbidden"));
        return http.build();
    }
}
