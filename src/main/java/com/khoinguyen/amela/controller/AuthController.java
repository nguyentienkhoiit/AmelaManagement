package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.authentication.EmailDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.LoginDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.service.AuthenticationService;
import com.khoinguyen.amela.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthController {
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDtoRequest());
        return "layout/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "layout/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("emailDto", new EmailDtoRequest());
        return "layout/auth/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String submitForgotPassword(
            HttpServletRequest httpServletRequest,
            Model model,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("emailDto") EmailDtoRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "layout/auth/forgot-password";
        }

        String rootUrl = UrlUtil.getApplicationUrl(httpServletRequest);
        ServiceResponse<String> serviceResponse = authenticationService.submitForgotPassword(request, rootUrl);
        redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
        return "redirect:/forgot-password";
    }

    @GetMapping("/new-password")
    public String newPassword(
            @RequestParam("token") String token,
            Model model
    ) {
        PasswordDtoRequest passwordDtoRequest = PasswordDtoRequest.builder()
                .token(token)
                .build();
        model.addAttribute("passwordDto", passwordDtoRequest);
        return "layout/auth/new-password";
    }

    @PostMapping("/new-password")
    public String submitNewPassword(
            Model model,
            @Valid @ModelAttribute("passwordDto") PasswordDtoRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "layout/auth/new-password";
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "confirmPassword",
                    "Password and confirm password does not match");
            return "layout/auth/new-password";
        }

        //submit new password
        ServiceResponse<String> serviceResponse = authenticationService.submitNewPassword(request);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/auth/new-password";
        }
        return "redirect:/login";
    }

    @GetMapping("/user-new-password")
    public String createNewPassword(
            @RequestParam("token") String token,
            Model model
    ) {
        PasswordDtoRequest passwordDtoRequest = PasswordDtoRequest.builder()
                .token(token)
                .build();
        model.addAttribute("passwordDto", passwordDtoRequest);
        return "/layout/users/user_new_password";
    }

    @PostMapping("/user-new-password")
    public String submitCreateNewPassword(
            Model model,
            @Valid @ModelAttribute("passwordDto") PasswordDtoRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "layout/users/user_new_password";
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "confirmPassword",
                    "Password and confirm password does not match");
            return "layout/users/user_new_password";
        }

        //submit new password
        ServiceResponse<String> serviceResponse = authenticationService.submitCreateNewPassword(request);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/users/user_new_password";
        }
        return "redirect:/login";
    }
}
