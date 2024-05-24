package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoResponse;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    HttpSession session;
    UserService userService;
    UserHelper userHelper;
    MessageScheduleService messageScheduleService;
    ValidationService validationService;
    OptionalValidator optionalValidator;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public String home(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest) {
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

    @GetMapping("dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String dashboard() {
        session.setAttribute("active", "dashboard");
        return "layout/auth/dashboard";
    }

    @GetMapping("profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewProfile(Model model) {
        session.setAttribute("active", "profile");

        var user = userService.getProfile();
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", user);
        }
        return "layout/auth/profile";
    }

    @PostMapping("profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String updateProfile(
            @RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
            @Valid @ModelAttribute("user") ProfileDtoRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        User userLoggedIn = userHelper.getUserLogin();
        User user = optionalValidator.findById(userLoggedIn.getId()).orElseThrow();
        ProfileDtoResponse response = UserMapper.toProfileUserDtoResponse(request, user);
        redirectAttributes.addFlashAttribute("user", response);

        // check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        userService.updateProfile(request, fileImage, errors);
        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/profile";
        }

        return "redirect:/profile";
    }
}
