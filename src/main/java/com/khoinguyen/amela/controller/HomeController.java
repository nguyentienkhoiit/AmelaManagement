package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.user.ProfileDtoResponse;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    HttpSession session;
    UserService userService;
    UserHelper userHelper;
    MessageScheduleService messageScheduleService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public String home(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest
    ) {
        session.setAttribute("active", "home");
        var pagingDtoResponse = messageScheduleService.getAllMessagesUser(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText().trim());
        }
        model.addAttribute("messages", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());
        return "layout/auth/home";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String dashboard() {
        session.setAttribute("active", "dashboard");
        return "layout/auth/dashboard";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewProfile(Model model) {
        session.setAttribute("active", "profile");

        var user = userService.getProfile();
        model.addAttribute("user", user);
        return "layout/auth/profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String updateProfile(
            @RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
            @Valid @ModelAttribute("user") ProfileDtoRequest request,
            BindingResult result,
            Model model
    ) {
        User userLoggedIn = userHelper.getUserLogin();

        ProfileDtoResponse response = UserMapper.toProfileUserDtoResponse(request, userLoggedIn);
        model.addAttribute("user", response);
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "layout/auth/profile";
        }

        ServiceResponse<String> serviceResponse = userService.updateProfile(request, fileImage);
        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/auth/profile";
        }

        return "redirect:/profile";
    }

    @GetMapping("/notFound")
    public String notFound404() {
        session.setAttribute("active", "error");
        return "layout/errorPages/not_found_404";
    }

    @GetMapping("/forbidden")
    public String forbidden403() {
        session.setAttribute("active", "error");
        return "layout/errorPages/forbidden_403";
    }

    @GetMapping("/badRequest")
    public String badRequest400() {
        session.setAttribute("active", "error");
        return "layout/errorPages/bad_request_400";
    }

    @GetMapping("/methodNotAllowed")
    public String methodNotAllowed403() {
        session.setAttribute("active", "error");
        return "layout/errorPages/method_not_allow_405";
    }

    @GetMapping("/internalServerError")
    public String internalServerError500() {
        session.setAttribute("active", "error");
        return "layout/errorPages/internal_server_error_500";
    }
}
