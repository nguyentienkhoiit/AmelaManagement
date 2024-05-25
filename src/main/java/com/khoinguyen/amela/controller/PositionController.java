package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.mapper.JobPositionMapper;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.util.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/positions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    HttpSession session;
    JobPositionService positionService;
    ValidationService validationService;

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
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        positionService.createPositions(request, errors);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
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

        if (!model.containsAttribute("position")) {
            JobPositionDtoResponse response = positionService.getPositionById(id);
            model.addAttribute("position", response);
        }

        return "layout/positions/position_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updatePositions(
            @Valid @ModelAttribute("position") JobPositionDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        positionService.updatePositions(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("position", JobPositionMapper.toJobPositionDtoResponse(request));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/positions/update/" + request.getId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        positionService.changeStatus(id);

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
