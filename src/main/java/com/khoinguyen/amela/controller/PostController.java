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
@RequestMapping("/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    HttpSession session;

    @GetMapping
    public String viewPosts(Model model) {
        session.setAttribute("active", "post");
        return "layout/posts/post_list";
    }

    @GetMapping("create")
    public String viewCreatePosts(Model model) {
        return "layout/posts/post_create";
    }

    @GetMapping("update")
    public String viewUpdatePosts(Model model) {
        return "layout/posts/post_update";
    }

    @GetMapping("detail")
    public String viewDetailPosts(Model model) {
        return "layout/posts/post_detail";
    }
}
