package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.excel.AttendanceExcel;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.UrlUtil;
import com.khoinguyen.amela.util.UserHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceController {
    HttpSession session;
    AttendanceService attendanceService;
    UserHelper userHelper;
    UserRepository userRepository;

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

        userId = (userLoggedIn.getRole().getName().equals(Constant.USER_NAME) || userId == null) ? userLoggedIn.getId() : userId;

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
    public String viewCreateAttendances(Model model, @PathVariable Long userId) {
        model.addAttribute("attendance", AttendanceDtoRequest.builder().userId(userId).build());
        return "layout/attendances/attendance_create";
    }

    @PostMapping("/create")
    public String createAttendances(
            Model model,
            @Valid @ModelAttribute AttendanceDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                redirectAttributes.addFlashAttribute(error.getField(), error.getDefaultMessage());
            }
            return "redirect:/attendances/create/" + request.getUserId();
        }

        ServiceResponse<String> serviceResponse = attendanceService.createAttendances(request);

        if (!serviceResponse.status()) {
            if (serviceResponse.column().equalsIgnoreCase("error")) {
                redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
                return "redirect:/attendances/create/" + request.getUserId() + "?error";
            }
            redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
            return "redirect:/attendances/create/" + request.getUserId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/update/{id}")
    public String viewUpdateAttendances(
            Model model,
            @PathVariable Long id
    ) {
        AttendanceDtoUpdateResponse response = attendanceService.getAttendanceById(id);
        model.addAttribute("attendance", response);
        return "layout/attendances/attendance_update";
    }

    @PostMapping("/update")
    public String updateAttendances(
            Model model,
            @Valid @ModelAttribute AttendanceDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error : fieldErrors) {
                redirectAttributes.addFlashAttribute(error.getField(), error.getDefaultMessage());
            }
            return "redirect:/attendances/update/" + request.getUserId();
        }

        ServiceResponse<String> serviceResponse = attendanceService.updateAttendances(request);

        if (!serviceResponse.status()) {
            if (serviceResponse.column().equalsIgnoreCase("error")) {
                redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
                return "redirect:/attendances/update/" + request.getUserId() + "?error";
            }
            redirectAttributes.addFlashAttribute(serviceResponse.column(), serviceResponse.data());
            return "redirect:/attendances/update/" + request.getUserId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/checkin")
    public String checkAttendance(HttpServletRequest request) {
        boolean rs = attendanceService.checkAttendance();
        return "redirect:/" + UrlUtil.getReferer(request);
    }

    @GetMapping("/change-status/{id}")
    public String changeStatus(
            @PathVariable Long id
    ) {
        boolean rs = attendanceService.changeStatus(id);
        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/exports/{userId}")
    public void exportAttendances(
            HttpServletResponse response,
            @PathVariable Long userId,
            @RequestParam(name = "text") String text
    ) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=attendances.xlsx";
        response.setHeader(headerKey, headerValue);

        PagingDtoRequest request = PagingDtoRequest.builder()
                .pageIndex("1")
                .pageSize("1000")
                .text(text)
                .build();
        List<AttendanceDtoResponse> attendanceDtoResponses = attendanceService.getAttendanceByUserId(request, userId).data();
        AttendanceExcel attendanceExcel = new AttendanceExcel(attendanceDtoResponses);
        attendanceExcel.export(response);
    }
}
