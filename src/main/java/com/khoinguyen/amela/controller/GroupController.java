package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.mapper.GroupMapper;
import com.khoinguyen.amela.service.GroupService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.ValidationService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupController {
    HttpSession session;
    GroupService groupService;
    ValidationService validationService;
    OptionalValidator optionalValidator;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewGroups(
            Model model,
            @ModelAttribute PagingDtoRequest request
    ) {
        session.setAttribute("active", "group");

        var pagingDtoResponse = groupService.getAllGroups(request);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (request.getText() != null) {
            model.addAttribute("text", request.getText());
        }
        model.addAttribute("groups", pagingDtoResponse.data());
        model.addAttribute("currentPage", request.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/groups?pageIndex=" + request.getPageIndex() +
                "&text=" + request.getText());

        return "layout/groups/group_list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateGroups(Model model) {
        session.setAttribute("active", "group");

        model.addAttribute("users", optionalValidator.findAllUser());
        model.addAttribute("group", new GroupDtoRequest());
        return "layout/groups/group_create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createGroups(
            @Valid @ModelAttribute("group") GroupDtoRequest request,
            BindingResult result,
            Model model
    ) {
        log.info("usersIds: {}", request.getUsersIds());
        //check validate
        model.addAttribute("users", optionalValidator.findAllUser());
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        groupService.createGroups(request, errors);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "layout/groups/group_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }


    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateGroups(
            Model model,
            @PathVariable Long id
    ) {
        session.setAttribute("active", "group");

        model.addAttribute("users", optionalValidator.findAllUser());
        if (!model.containsAttribute("group")) {
            GroupDtoResponse response = groupService.getGroupById(id);
            model.addAttribute("group", response);
        }
        return "layout/groups/group_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateGroups(
            @Valid @ModelAttribute("group") GroupDtoRequest request,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        groupService.updateGroups(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("group", GroupMapper.toGroupDtoResponse(request));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/groups/update/" + request.getId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(@PathVariable Long id) {
        boolean rs = groupService.changeStatus(id);

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
