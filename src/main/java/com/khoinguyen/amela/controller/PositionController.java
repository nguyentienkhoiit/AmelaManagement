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
@RequestMapping("/positions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    HttpSession session;

    @GetMapping
    public String viewPositions(Model model) {
        session.setAttribute("active", "position");
        return "layout/positions/position_list";
    }

    @GetMapping("create")
    public String viewCreatePositions(Model model) {
        return "layout/positions/position_create";
    }

    @GetMapping("update")
    public String viewUpdatePositions(Model model) {
        return "layout/positions/position_update";
    }
}
