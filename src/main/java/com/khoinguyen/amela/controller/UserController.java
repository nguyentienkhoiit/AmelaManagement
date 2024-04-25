package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.service.RoleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.OptionalValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

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
    OptionalValidator optionalValidator;

    @GetMapping
    public String viewUsers(Model model, @ModelAttribute PagingDtoRequest pagingDtoRequest) {
        session.setAttribute("active", "user");

        var pagingDtoResponse = userService.getAllUsers(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText());
        }
        model.addAttribute("users", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/users?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());
        return "layout/users/user_list";
    }

    @GetMapping("create")
    public String viewCreateUsers(Model model) {
        SetSessionSelectionOption(model);
        return "layout/users/user_create";
    }

    @PostMapping("create")
    public String createUser(
            @Valid @ModelAttribute("user") UserDtoRequest request,
            BindingResult result,
            Model model
    ) {
        //check validate
        if (result.hasErrors()) {
            return "layout/users/user_create";
        }

        if (optionalValidator.findByEmailExist(request.getEmail()).isPresent()) {
            result.rejectValue("email", "email.exist", "Email already exists");
            return "layout/users/user_create";
        }

        if (optionalValidator.findByUsernameExist(request.getUsername()).isPresent()) {
            result.rejectValue("username", "username.exist", "Username already exists");
            return "layout/users/user_create";
        }

        if (optionalValidator.findByPhoneExist(request.getPhone()).isPresent()) {
            result.rejectValue("phone", "phone.exist", "Phone already exists");
            return "layout/users/user_create";
        }

        LocalDate dateOfBirth = request.getDateOfBirth();
        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfBirth, now);
        if (period.getYears() < 18) {
            result.rejectValue("dateOfBirth", "dateOfBirth.badRequest", "Date of birth must be greater than 18");
            return "layout/users/user_create";
        }

        if (optionalValidator.findByJobPositionId(request.getJobPositionId()).isEmpty()) {
            return "redirect:/users/create";
        }

        if (optionalValidator.findByDepartmentId(request.getDepartmentId()).isEmpty()) {
            return "redirect:/users/create";
        }

        if (optionalValidator.findByRoleId(request.getRoleId()).isEmpty()) {
            return "redirect:/users/create";
        }

        //save to database
        userService.createUser(request);

        //remove session
        session.removeAttribute("roles");
        session.removeAttribute("departments");
        session.removeAttribute("jobPositions");

        return "redirect:/users";
    }

    @GetMapping("update/{id}")
    public String viewUpdateUsers(Model model, @PathVariable Long id) {
        UserDtoResponse userDtoResponse = userService.getUserById(id);
        if(userDtoResponse == null) return "redirect:/error-page";

        SetSessionSelectionOption(model);
        model.addAttribute("user", userDtoResponse);
        return "layout/users/user_update";
    }

    @PostMapping("/update/{id}")
    public String updateUser(
            Model model,
            @Valid @ModelAttribute("user") UserDtoRequest request,
            BindingResult result,
            @PathVariable Long id
    ) {
        boolean rs = userService.updateUser(request, id);
        return "redirect:/users";
    }

    @GetMapping("reset-password/{id}")
    public String resetPassword(@PathVariable Long id) {
        boolean rs = userService.resetPassword(id);
        return "redirect:/users/update/" + id;
    }

    @GetMapping("/change-status/{id}")
    public String changeStatus(@PathVariable Long id) {
        boolean rs = userService.changeStatus(id);
        return "redirect:/users";
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
