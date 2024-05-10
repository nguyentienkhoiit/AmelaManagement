package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.service.DepartmentService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    HttpSession session;
    DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewDepartments(
            Model model,
            @ModelAttribute PagingDtoRequest request
    ) {
        session.setAttribute("active", "department");

        var pagingDtoResponse = departmentService.getAllGroups(request);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (request.getText() != null) {
            model.addAttribute("text", request.getText());
        }
        model.addAttribute("departments", pagingDtoResponse.data());
        model.addAttribute("currentPage", request.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/departments?pageIndex=" + request.getPageIndex() +
                "&text=" + request.getText());
        return "layout/departments/department_list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateDepartments(Model model) {
        model.addAttribute("department", new DepartmentDtoRequest());
        return "layout/departments/department_create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createDepartments(
            @Valid @ModelAttribute("department") DepartmentDtoRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "layout/departments/department_create";
        }

        ServiceResponse<String> serviceResponse = departmentService.createDepartments(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/departments/department_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateDepartments(
            Model model,
            @PathVariable Long id
    ) {
        session.setAttribute("active", "department");

        DepartmentDtoResponse response = departmentService.getDepartmentById(id);
        model.addAttribute("department", response);
        return "layout/departments/department_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDepartments(
            @Valid @ModelAttribute("department") DepartmentDtoRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "layout/departments/department_update";
        }

        ServiceResponse<String> serviceResponse = departmentService.updateDepartments(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/departments/department_update";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        boolean rs = departmentService.changeStatus(id);

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
