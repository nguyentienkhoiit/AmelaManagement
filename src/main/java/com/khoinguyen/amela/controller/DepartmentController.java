package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.mapper.DepartmentMapper;
import com.khoinguyen.amela.service.DepartmentService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/departments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    HttpSession session;
    DepartmentService departmentService;
    ValidationService validationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewDepartments(
            Model model,
            @ModelAttribute PagingDtoRequest request
    ) {
        session.setAttribute("active", "department");

        var pagingDtoResponse = departmentService.getAllDepartments(request);
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
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        departmentService.createDepartments(request, errors);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
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

        if (!model.containsAttribute("department")) {
            DepartmentDtoResponse response = departmentService.getDepartmentById(id);
            model.addAttribute("department", response);
        }
        return "layout/departments/department_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDepartments(
            @Valid @ModelAttribute("department") DepartmentDtoRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        departmentService.updateDepartments(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            redirectAttributes.addFlashAttribute("department", DepartmentMapper.toDepartmentDtoResponse(request));
            return "redirect:/departments/update/" + request.getId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        departmentService.changeStatus(id);

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
