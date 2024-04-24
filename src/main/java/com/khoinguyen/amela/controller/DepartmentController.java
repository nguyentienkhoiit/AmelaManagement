package com.khoinguyen.amela.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    HttpSession session;

    @GetMapping
    public String viewDepartments(Model model) {
        session.setAttribute("active", "department");
        return "layout/departments/department_list";
    }

    @GetMapping("create")
    public String viewCreateDepartments(Model model) {
        return "layout/departments/department_create";
    }

    @GetMapping("update")
    public String viewUpdateDepartments(Model model) {
        return "layout/departments/department_update";
    }
}
