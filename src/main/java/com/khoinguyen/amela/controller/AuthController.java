package com.khoinguyen.amela.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "layout/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "layout/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "layout/auth/forgot-password";
    }

    @GetMapping("/new-password")
    public String newPassword() {
        return "layout/auth/new-password";
    }
}
