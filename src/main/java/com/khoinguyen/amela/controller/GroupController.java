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
@RequestMapping("/groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupController {
    HttpSession session;

    @GetMapping
    public String viewGroups(Model model) {
        session.setAttribute("active", "group");
        return "layout/groups/group_list";
    }

    @GetMapping("create")
    public String viewCreateGroups(Model model) {
        return "layout/groups/group_create";
    }

    @GetMapping("update")
    public String viewUpdateGroups(Model model) {
        return "layout/groups/group_update";
    }
}
