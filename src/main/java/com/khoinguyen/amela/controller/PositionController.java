package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.service.JobPositionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/positions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    HttpSession session;
    JobPositionService positionService;
    ApplicationContext applicationContext;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewPositions(
            Model model,
            @ModelAttribute PagingDtoRequest request
    ) {
        session.setAttribute("active", "position");

        var pagingDtoResponse = positionService.getAllPositions(request);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (request.getText() != null) {
            model.addAttribute("text", request.getText());
        }
        model.addAttribute("positions", pagingDtoResponse.data());
        model.addAttribute("currentPage", request.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/positions?pageIndex=" + request.getPageIndex() +
                "&text=" + request.getText());

        return "layout/positions/position_list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreatePositions(Model model) {
        model.addAttribute("position", new JobPositionDtoResponse());
        return "layout/positions/position_create";
    }

    @PostMapping("/create")
    public String createPositions(
            @Valid @ModelAttribute("position") JobPositionDtoRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "layout/positions/position_create";
        }

        ServiceResponse<String> serviceResponse = positionService.createPositions(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/positions/position_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdatePositions(
            Model model,
            @PathVariable Long id
    ) {
        session.setAttribute("active", "position");

        JobPositionDtoResponse response = positionService.getPositionById(id);
        model.addAttribute("position", response);
        return "layout/positions/position_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updatePositions(
            @Valid @ModelAttribute("position") JobPositionDtoRequest request,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "layout/positions/position_update";
        }

        ServiceResponse<String> serviceResponse = positionService.updatePositions(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/positions/position_update";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        boolean rs = positionService.changeStatus(id);

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
