package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.service.GroupService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/groups")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupController {
    HttpSession session;
    GroupService groupService;

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
        if (result.hasErrors()) {
            return "layout/groups/group_create";
        }

        ServiceResponse<String> serviceResponse = groupService.createGroups(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
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

        GroupDtoResponse response = groupService.getGroupById(id);
        model.addAttribute("group", response);
        return "layout/groups/group_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateGroups(
            @Valid @ModelAttribute("group") GroupDtoRequest request,
            BindingResult result,
            Model model
    ) {

        if (result.hasErrors()) {
            return "layout/groups/group_update";
        }

        ServiceResponse<String> serviceResponse = groupService.updateGroups(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/groups/group_update";
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
