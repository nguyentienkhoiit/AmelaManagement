package com.khoinguyen.amela.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khoinguyen.amela.entity.enums.ErrorResponse;

@Controller
@RequestMapping("/")
public class ErrorController {
    @GetMapping("notFound")
    public String notFound404(Model model) {
        model.addAttribute("title", ErrorResponse.SC_NOT_FOUND.getTitle());
        model.addAttribute("message", ErrorResponse.SC_NOT_FOUND.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("forbidden")
    public String forbidden403(Model model) {
        model.addAttribute("title", ErrorResponse.SC_FORBIDDEN.getTitle());
        model.addAttribute("message", ErrorResponse.SC_FORBIDDEN.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("unauthorized")
    public String authorize401(Model model) {
        model.addAttribute("title", ErrorResponse.SC_FORBIDDEN.getTitle());
        model.addAttribute("message", ErrorResponse.SC_FORBIDDEN.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("badRequest")
    public String badRequest400(Model model) {
        model.addAttribute("title", ErrorResponse.SC_BAD_REQUEST.getTitle());
        model.addAttribute("message", ErrorResponse.SC_BAD_REQUEST.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("methodNotAllowed")
    public String methodNotAllowed405(Model model) {
        model.addAttribute("title", ErrorResponse.SC_METHOD_NOT_ALLOWED.getTitle());
        model.addAttribute("message", ErrorResponse.SC_METHOD_NOT_ALLOWED.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("internalServerError")
    public String internalServerError500(Model model) {
        model.addAttribute("title", ErrorResponse.SC_INTERNAL_SERVER_ERROR.getTitle());
        model.addAttribute("message", ErrorResponse.SC_INTERNAL_SERVER_ERROR.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("requestsTimeout")
    public String requestTimeout408(Model model) {
        model.addAttribute("title", ErrorResponse.SC_REQUESTS_TIMEOUT.getTitle());
        model.addAttribute("message", ErrorResponse.SC_REQUESTS_TIMEOUT.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("tooManyRequests")
    public String tooManyRequests429(Model model) {
        model.addAttribute("title", ErrorResponse.SC_TOO_MANY_REQUESTS.getTitle());
        model.addAttribute("message", ErrorResponse.SC_TOO_MANY_REQUESTS.getErrorMessage());
        return "layout/auth/error";
    }

    @GetMapping("badGateway")
    public String badGateway502(Model model) {
        model.addAttribute("title", ErrorResponse.SC_BAD_GATEWAY.getTitle());
        model.addAttribute("message", ErrorResponse.SC_BAD_GATEWAY.getErrorMessage());
        return "layout/auth/error";
    }
}
