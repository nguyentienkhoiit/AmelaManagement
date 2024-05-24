package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingUserDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.service.RoleService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.FileHelper;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    FileHelper fileHelper;
    ValidationService validationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewUsers(
            Model model,
            @ModelAttribute PagingUserDtoRequest pagingDtoRequest
    ) {
        session.setAttribute("active", "user");

        User userLoggedIn = userHelper.getUserLogin();
        var pagingDtoResponse = userService.getAllUsers(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText().trim());
        }
        setInfoSelectionOption(model);
        model.addAttribute("users", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userLoggedIn", userLoggedIn);

        String url = "/users?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText();

        if (pagingDtoRequest.getDepartmentId() != null) {
            model.addAttribute("departmentId", pagingDtoRequest.getDepartmentId());
            url += "&departmentId=" + pagingDtoRequest.getDepartmentId();
        }

        if (pagingDtoRequest.getPositionId() != null) {
            model.addAttribute("positionId", pagingDtoRequest.getPositionId());
            url += "&positionId=" + pagingDtoRequest.getPositionId();
        }

        if (pagingDtoRequest.getGroupId() != null) {
            model.addAttribute("groupId", pagingDtoRequest.getGroupId());
            url += "&groupId=" + pagingDtoRequest.getGroupId();
        }

        session.setAttribute("url", url);
        return "layout/users/user_list";
    }

    @GetMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateUsers(
            Model model,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long positionId
    ) {
        UserDtoRequest user = UserDtoRequest.builder().build();
        if (departmentId != null) user.setDepartmentId(departmentId);
        if (positionId != null) user.setJobPositionId(positionId);

        model.addAttribute("user", user);
        setInfoSelectionOption(model);
        return "layout/users/user_create";
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createUser(
            @Valid @ModelAttribute("user") UserDtoRequest request,
            BindingResult result,
            Model model
    ) {
        //set select option
        setInfoSelectionOption(model);

        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        //save to database
        userService.createUser(request, errors);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "layout/users/user_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateUsers(Model model, @PathVariable Long id) {
        setInfoSelectionOption(model);
        var userDtoResponse = userService.getUserById(id);
        if (userDtoResponse == null) return "redirect:/error-page";

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", userDtoResponse);
        }
        return "layout/users/user_update";
    }

    public void setObjectUserUpdate(Model model, RedirectAttributes redirectAttributes, UserDtoUpdate request) {
        JobPositionDtoResponse jobPositionDtoResponse = jobPositionService
                .findById(request.getJobPositionId());
        DepartmentDtoResponse departmentDtoResponse = departmentService
                .findById(request.getDepartmentId());
        RoleDtoResponse roleDtoResponse = roleService
                .findById(request.getRoleId());

        redirectAttributes.addFlashAttribute("user",
                UserMapper.toUserDtoResponse(
                        request,
                        departmentDtoResponse,
                        roleDtoResponse,
                        jobPositionDtoResponse
                )
        );
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(
            Model model,
            @Valid @ModelAttribute("user") UserDtoUpdate request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        setInfoSelectionOption(model);
        setObjectUserUpdate(model, redirectAttributes, request);

        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        //save to database
        userService.updateUser(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/users/update/" + request.getId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("reset-password/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String resetPassword(@PathVariable Long id) {
        boolean rs = userService.resetPassword(id);
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

    private void setInfoSelectionOption(Model model) {
        var roleDtoResponses = roleService.findAll();
        var departmentDtoResponses = departmentService.findAll();
        var jobPositionDtoResponses = jobPositionService.findAll();

        model.addAttribute("roles", roleDtoResponses);
        model.addAttribute("departments", departmentDtoResponses);
        model.addAttribute("jobPositions", jobPositionDtoResponses);
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

    @GetMapping("/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<Resource> getImage() {
        User user = userHelper.getUserLogin();
        Resource file = fileHelper.load(user.getAvatar());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.getAvatar() + "\"").body(file);
    }
}
