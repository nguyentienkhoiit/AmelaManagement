package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.configuration.AppConfig;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.excel.AttendanceExcel;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.mapper.AttendanceMapper;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.khoinguyen.amela.util.Constant.IN_DAY_EDITED;

@Slf4j
@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceController {
    HttpSession session;
    AttendanceService attendanceService;
    UserHelper userHelper;
    AppConfig appConfig;
    ValidationService validationService;
    UserService userService;

    @GetMapping(value = {"", "/{userId}"})
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewAttendances(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest,
            @PathVariable(required = false) Long userId
    ) {
        session.setAttribute("active", "attendance");
        User userLoggedIn = userHelper.getUserLogin();

        userId = (userLoggedIn.getRole().getName()
                .equals(Constant.USER_NAME) || userId == null) ? userLoggedIn.getId() : userId;

        var pagingDtoResponse = attendanceService.getAttendanceByUserId(pagingDtoRequest, userId);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());

        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText());
        }
        model.addAttribute("attendances", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("userId", userId);
        session.setAttribute("url", "/attendances/" + userId + "?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());

        return "layout/attendances/attendance_list";
    }

    @GetMapping("/create/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateAttendances(Model model, @PathVariable Long userId) {
        if (!model.containsAttribute("attendance")) {
            model.addAttribute("attendance", AttendanceDtoRequest.builder().userId(userId).build());
        }
        return "layout/attendances/attendance_create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createAttendances(
            @Valid @ModelAttribute AttendanceDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("attendance", request);

        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        attendanceService.createAttendances(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/attendances/create/" + request.getUserId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateAttendances(
            Model model,
            @PathVariable Long id
    ) {
        if (!model.containsAttribute("attendance")) {
            AttendanceDtoUpdateResponse response = attendanceService.getAttendanceById(id);
            if (!response.isExpired()) return "redirect:/forbidden";
            model.addAttribute("attendance", response);
        }
        return "layout/attendances/attendance_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateAttendances(
            @Valid @ModelAttribute AttendanceDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (!DateTimeHelper.isExpiredDay(request.getCheckDay(), IN_DAY_EDITED))
            return "redirect:/forbidden";
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }
        attendanceService.updateAttendances(request, errors);

        if (!errors.isEmpty()) {
            var response = AttendanceMapper.toAttendanceDtoUpdateResponse(request);
            redirectAttributes.addFlashAttribute("attendance", response);
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/attendances/update/" + request.getAttendanceId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/checkin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String checkAttendance(HttpServletRequest request) {
        attendanceService.checkAttendance();
        return "redirect:/" + UrlUtil.getReferer(request, appConfig.HOST);
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(
            @PathVariable Long id
    ) {
        attendanceService.changeStatus(id);
        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/exports/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void exportAttendances(
            HttpServletResponse response,
            @PathVariable Long userId,
            @RequestParam(name = "text") String text
    ) throws IOException {
        UserDtoResponse user = userService.getUserById(userId);
        if (user == null) return;

        String fileName = String.format("%s - %s - %s %s.xlsx",
                user.getDepartment().getName(),
                user.getCode(),
                user.getFirstname(),
                user.getLastname()
        );

        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replace("+", "%20");

        PagingDtoRequest request = PagingDtoRequest.builder()
                .pageIndex(Constant.PAGE_INDEX.toString())
                .pageSize(String.valueOf(Integer.MAX_VALUE))
                .text(text)
                .build();

        var attendanceDtoResponses = attendanceService.getAttendanceByUserId(request, userId).data();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=%s", encodedFileName);
        response.setHeader(headerKey, headerValue);

        AttendanceExcel attendanceExcel = new AttendanceExcel(attendanceDtoResponses);
        attendanceExcel.export(response);
    }
}
