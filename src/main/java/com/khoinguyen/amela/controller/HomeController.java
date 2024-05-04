package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.service.RoleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    HttpSession session;
    UserService userService;
    RoleService roleService;
    DepartmentService departmentService;
    JobPositionService jobPositionService;

    @GetMapping
    public String home() {
        session.setAttribute("active", "home");
        return "layout/auth/home";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        session.setAttribute("active", "dashboard");
        return "layout/auth/dashboard";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        var user = userService.getProfile();
        var roleDtoResponses = roleService.findAll();
        var departmentDtoResponses = departmentService.findAll();
        var jobPositionDtoResponses = jobPositionService.findAll();

        model.addAttribute("user", user);
        session.setAttribute("roles", roleDtoResponses);
        session.setAttribute("departments", departmentDtoResponses);
        session.setAttribute("jobPositions", jobPositionDtoResponses);
        session.setAttribute("active", "profile");
        return "layout/auth/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @Valid @ModelAttribute("user") UserDtoRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "layout/auth/profile";
        }

        ServiceResponse<String> serviceResponse = userService.updateProfile(request);
        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/auth/profile";
        }

        //remove session
        session.removeAttribute("roles");
        session.removeAttribute("departments");
        session.removeAttribute("jobPositions");

        return "redirect:/profile";
    }

    @GetMapping("error-page")
    public String viewError() {
        session.setAttribute("active", "error");
        return "layout/error";
    }
}
