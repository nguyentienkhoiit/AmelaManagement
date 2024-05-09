package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.service.RoleService;
import com.khoinguyen.amela.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    HttpSession session;
    UserService userService;
    RoleService roleService;
    DepartmentService departmentService;
    JobPositionService jobPositionService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewUsers(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest
    ) {
        session.setAttribute("active", "user");

        var pagingDtoResponse = userService.getAllUsers(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText().trim());
        }
        model.addAttribute("users", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/users?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());
        return "layout/users/user_list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateUsers(Model model) {
        SetSessionSelectionOption(model);
        return "layout/users/user_create";
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createUser(
            @Valid @ModelAttribute("user") UserDtoRequest request,
            BindingResult result,
            Model model
    ) {
        //check validate
        if (result.hasErrors()) {
            return "layout/users/user_create";
        }
        //save to database
        ServiceResponse<String> serviceResponse = userService.createUser(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/users/user_create";
        }

        //remove session
        session.removeAttribute("roles");
        session.removeAttribute("departments");
        session.removeAttribute("jobPositions");

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(
            Model model,
            @Valid @ModelAttribute("user") UserDtoUpdate request,
            BindingResult result
    ) {
        //check validate
        if (result.hasErrors()) {
            return "layout/users/user_update";
        }

        //save to database
        ServiceResponse<String> serviceResponse = userService.updateUser(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/users/user_update";
        }

        //remove session
        session.removeAttribute("roles");
        session.removeAttribute("departments");
        session.removeAttribute("jobPositions");

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateUsers(Model model, @PathVariable Long id) {
        UserDtoResponse userDtoResponse = userService.getUserById(id);
        if (userDtoResponse == null) return "redirect:/error-page";

        SetSessionSelectionOption(model);
        model.addAttribute("user", userDtoResponse);
        return "layout/users/user_update";
    }

    @GetMapping("reset-password/{id}")
    public String resetPassword(@PathVariable Long id) {
        boolean rs = userService.resetPassword(id);
        return "redirect:/users/update/" + id;
    }

    @GetMapping("/change-status/{id}")
    public String changeStatus(@PathVariable Long id) {
        boolean rs = userService.changeStatus(id);
        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    private void SetSessionSelectionOption(Model model) {
        var roleDtoResponses = roleService.findAll();
        var departmentDtoResponses = departmentService.findAll();
        var jobPositionDtoResponses = jobPositionService.findAll();

        session.setAttribute("roles", roleDtoResponses);
        session.setAttribute("departments", departmentDtoResponses);
        session.setAttribute("jobPositions", jobPositionDtoResponses);

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", UserDtoRequest.builder().build());
        }
    }
}
