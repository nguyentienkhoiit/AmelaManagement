package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceController {
    HttpSession session;
    AttendanceService attendanceService;
    UserHelper userHelper;

    @GetMapping(value = {"", "/{userId}"})
    public String viewAttendances(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest,
            @PathVariable(required = false) Long userId,
            HttpServletRequest request
    ) {
        log.info("request {}", request.getRequestURL());
        session.setAttribute("active", "attendance");
        User userLoggedIn = userHelper.getUserLogin();

        userId = (userLoggedIn.getRole().getId() == 2 || userId == null) ? userLoggedIn.getId() : userId;

        var pagingDtoResponse = attendanceService.getAttendanceByUserId(pagingDtoRequest, userId);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());

        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText());
        }
        model.addAttribute("attendances", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);
        session.setAttribute("url", "/attendances?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());

        return "layout/attendances/attendance_list";
    }

    @GetMapping("create")
    public String viewCreateAttendances(Model model) {
        return "layout/attendances/attendance_create";
    }

    @GetMapping("update")
    public String viewUpdateAttendances(Model model) {
        return "layout/attendances/attendance_update";
    }

    @GetMapping("/checkin")
    public String checkAttendance() {
        boolean rs = attendanceService.checkAttendance();
        return "layout/auth/home";
    }
}
