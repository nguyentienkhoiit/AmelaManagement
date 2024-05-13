package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.service.RoleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.UserHelper;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    UserHelper userHelper;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewUsers(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest
    ) {
        session.setAttribute("active", "user");

        User userLoggedIn = userHelper.getUserLogin();
        var pagingDtoResponse = userService.getAllUsers(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText().trim());
        }
        model.addAttribute("users", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userLoggedIn", userLoggedIn);

        session.setAttribute("url", "/users?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());
        return "layout/users/user_list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateUsers(Model model) {
        model.addAttribute("user", UserDtoRequest.builder().build());
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

    @GetMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateUsers(Model model, @PathVariable Long id) {
        UserDtoResponse userDtoResponse = userService.getUserById(id);
        if (userDtoResponse == null) return "redirect:/error-page";

        SetSessionSelectionOption(model);
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", userDtoResponse);
        }
        return "layout/users/user_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(
            Model model,
            @Valid @ModelAttribute("user") UserDtoUpdate request,
            BindingResult result
    ) {
        JobPositionDtoResponse jobPositionDtoResponse = jobPositionService
                .findById(request.getJobPositionId());
        DepartmentDtoResponse departmentDtoResponse = departmentService
                .findById(request.getDepartmentId());
        RoleDtoResponse roleDtoResponse = roleService
                .findById(request.getRoleId());

        model.addAttribute("user",
                UserMapper.toUserDtoResponse(
                        request,
                        departmentDtoResponse,
                        roleDtoResponse,
                        jobPositionDtoResponse
                )
        );

        //check validate
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "layout/users/user_update";
        }

        //save to database
        ServiceResponse<String> serviceResponse = userService.updateUser(request);

        if (!serviceResponse.status()) {
            model.addAttribute(serviceResponse.column(), serviceResponse.data());
            return "layout/users/user_update";
        }

        //remove session
        session.removeAttribute("roles");
        session.removeAttribute("departments");
        session.removeAttribute("jobPositions");

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("reset-password/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String resetPassword(@PathVariable Long id) {
        boolean rs = userService.resetPassword(id);
        if (!rs) return "redirect:/forbidden";
        return "redirect:/users/update/" + id;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        if (Objects.equals(userLoggedIn.getId(), id))
            return "redirect:/forbidden";

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
    }

    @GetMapping("/send-token-again/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String sendTokenAgain(
            Model model,
            @PathVariable Long id
    ) {
        boolean rs = userService.sendTokenAgain(id);
        return "redirect:/users/update/" + id;
    }
}
