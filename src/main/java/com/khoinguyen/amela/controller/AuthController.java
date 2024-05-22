package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.authentication.ChangePasswordDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.EmailDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.LoginDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.service.AuthenticationService;
import com.khoinguyen.amela.service.VerificationService;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthController {
    AuthenticationService authenticationService;
    VerificationService verificationService;
    ValidationService validationService;
    OptionalValidator optionalValidator;
    HttpSession session;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new LoginDtoRequest());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            if (roles.contains(Constant.ADMIN_NAME)) {
                return "redirect:/dashboard";
            } else if (roles.contains(Constant.USER_NAME)) {
                return "redirect:/";
            }
        }
        return "layout/auth/login";
    }

    @GetMapping("/oauth2")
    public String auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = authenticationService.loginOauth2(authentication);
        if (user == null) {

            return "redirect:/login?fault";
        }
        if (user.getRole().getName().equalsIgnoreCase(Constant.ADMIN_NAME)) {
            return "redirect:/dashboard";
        } else if (user.getRole().getName().equalsIgnoreCase(Constant.USER_NAME)) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register() {
        return "layout/auth/register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        if (!model.containsAttribute("emailDto")) {
            model.addAttribute("emailDto", new EmailDtoRequest());
        }
        return "layout/auth/forgot-password";
    }

    /**
     * Forgot password
     *
     * @param model              model
     * @param redirectAttributes redirect
     * @param request            request
     * @param result             result
     * @return "layout/auth/forgot-password"
     */
    @PostMapping("/forgot-password")
    public String submitForgotPassword(
            Model model,
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("emailDto") EmailDtoRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "layout/auth/forgot-password";
        }

        ServiceResponse<String> serviceResponse = authenticationService.submitForgotPassword(request);
        redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
        redirectAttributes.addFlashAttribute("emailDto", request);
        return "redirect:/forgot-password";
    }

    @GetMapping("/new-password")
    public String newPassword(
            @RequestParam("token") String token,
            Model model
    ) {
        if (!model.containsAttribute("passwordDto")) {
            PasswordDtoRequest passwordDtoRequest = PasswordDtoRequest.builder()
                    .token(token)
                    .build();
            model.addAttribute("passwordDto", passwordDtoRequest);
        }

        ServiceResponse<String> serviceResponse = verificationService.validateToken(token, true);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
        }
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

        //submit new password
        ServiceResponse<String> serviceResponse = authenticationService.submitNewPassword(request);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/auth/new-password";
        }
        return "redirect:/login";
    }

    /**
     * Activate new user
     *
     * @param token token
     * @param model model
     * @return "/layout/users/user_new_password"
     */
    @GetMapping("/user-new-password")
    public String createNewPassword(
            @RequestParam("token") String token,
            Model model
    ) {
        ServiceResponse<String> serviceResponse = verificationService.validateToken(token, false);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
        }

        if (!model.containsAttribute("passwordDto")) {
            PasswordDtoRequest passwordDtoRequest = PasswordDtoRequest.builder()
                    .token(token)
                    .build();
            model.addAttribute("passwordDto", passwordDtoRequest);
        }
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

        //submit new password
        ServiceResponse<String> serviceResponse = authenticationService.submitCreateNewPassword(request);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/users/user_new_password";
        }
        return "redirect:/login";
    }

    /**
     * Change password
     *
     * @param model model
     * @return "layout/auth/change-password"
     */
    @GetMapping("/change-password")
    public String changePassword(Model model) {
        if (!model.containsAttribute("passwordDto")) {
            model.addAttribute("user", new ChangePasswordDtoRequest());
        }
        return "layout/auth/change-password";
    }

    @PostMapping("/change-password")
    public String submitChangePassword(
            Model model,
            @Valid @ModelAttribute("user") ChangePasswordDtoRequest request,
            BindingResult result
    ) {
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        authenticationService.submitChangePassword(request, errors);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "layout/auth/change-password";
        }
        return "redirect:/profile";
    }
}
