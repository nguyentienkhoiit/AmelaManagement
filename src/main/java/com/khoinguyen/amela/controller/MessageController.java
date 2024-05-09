package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.service.GroupService;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.util.PermissionMessages;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    HttpSession session;
    MessageScheduleService messageScheduleService;
    GroupService groupService;
    PermissionMessages permissionMessages;

    @GetMapping
    public String viewMessagesAdmin(
            Model model,
            @ModelAttribute PagingDtoRequest pagingDtoRequest
    ) {
        session.setAttribute("active", "message");

        var pagingDtoResponse = messageScheduleService.getAllMessagesAdmin(pagingDtoRequest);
        var totalPage = pagingDtoResponse.getTotalPageList(pagingDtoResponse.data());
        if (pagingDtoRequest.getText() != null) {
            model.addAttribute("text", pagingDtoRequest.getText().trim());
        }
        model.addAttribute("messages", pagingDtoResponse.data());
        model.addAttribute("currentPage", pagingDtoRequest.getPageIndex());
        model.addAttribute("totalPage", totalPage);

        session.setAttribute("url", "/messages?pageIndex=" + pagingDtoRequest.getPageIndex() +
                "&text=" + pagingDtoRequest.getText());
        return "layout/messages/message_list";
    }

    @GetMapping("/detail/{id}")
    public String viewMessageDetail(
            Model model,
            @PathVariable long id
    ) {
        //check permission
        if (!permissionMessages.checkPermission(id)) {
            return "redirect:/error-page";
        }

        MessageScheduleUpdateResponse response = messageScheduleService.getByMessageScheduleId(id, "detail");
        model.addAttribute("message", response);
        return "layout/messages/message_detail";
    }

    @GetMapping("/create")
    public String viewCreateMessages(Model model) {
        var groups = groupService.getAll();
        session.setAttribute("groups", groups);

        model.addAttribute("message", new MessageScheduleDtoRequest());
        return "layout/messages/message_create";
    }

    @PostMapping("/create")
    public String createMessages(
            @Valid @ModelAttribute("message") MessageScheduleDtoRequest request,
            BindingResult result,
            Model model
    ) {
        //check validate
        if (result.hasErrors()) {
            return "layout/messages/message_create";
        }
        //save to database
        ServiceResponse<String> serviceResponse = messageScheduleService.createMessages(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/messages/message_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("update/{id}")
    public String viewUpdateMessages(Model model, @PathVariable Long id) {
        MessageScheduleUpdateResponse messageScheduleDtoResponse = messageScheduleService.getByMessageScheduleId(id, "id");
        model.addAttribute("message", messageScheduleDtoResponse);

        var groups = groupService.getAll();
        session.setAttribute("groups", groups);
        return "layout/messages/message_update";
    }

    @PostMapping("/update")
    public String updateMessages(
            Model model,
            @Valid @ModelAttribute("message") MessageScheduleDtoRequest request,
            BindingResult result
    ) {
        //check validate
        if (result.hasErrors()) {
            return "layout/messages/message_update";
        }

        //save to database
        ServiceResponse<String> serviceResponse = messageScheduleService.updateMessages(request);

        if (!serviceResponse.status()) {
            result.rejectValue(serviceResponse.column(), serviceResponse.column(), serviceResponse.data());
            return "layout/messages/message_update";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    public String changeStatus(
            Model model,
            @PathVariable Long id
    ) {
        boolean rs = messageScheduleService.changeStatus(id);
        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
