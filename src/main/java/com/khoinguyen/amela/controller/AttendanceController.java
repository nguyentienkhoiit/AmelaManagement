package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceController {
    HttpSession session;
    AttendanceService attendanceService;

    @GetMapping
    public String viewAttendances(Model model) {
        session.setAttribute("active", "attendance");



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
