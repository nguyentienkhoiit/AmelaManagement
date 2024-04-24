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
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    HttpSession session;

    @GetMapping
    public String viewMessages(Model model) {
        session.setAttribute("active", "message");
        return "layout/messages/message_list";
    }

    @GetMapping("create")
    public String viewCreateMessages(Model model) {
        return "layout/messages/message_create";
    }

    @GetMapping("update")
    public String viewUpdateMessages(Model model) {
        return "layout/messages/message_update";
    }
}
