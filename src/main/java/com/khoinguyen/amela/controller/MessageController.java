package com.khoinguyen.amela.controller;

import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.mapper.MessageScheduleMapper;
import com.khoinguyen.amela.service.GroupService;
import com.khoinguyen.amela.service.MessageScheduleService;
import com.khoinguyen.amela.util.PermissionMessages;
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
@RequestMapping("/messages")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    HttpSession session;
    MessageScheduleService messageScheduleService;
    GroupService groupService;
    PermissionMessages permissionMessages;
    ValidationService validationService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String viewMessageDetail(
            Model model,
            @PathVariable long id
    ) {
        //check permission
        if (!permissionMessages.checkPermission(id)) {
            return "redirect:/error-page";
        }
        List<MessageScheduleDtoResponse> topMessages = messageScheduleService
                .getTopMessagesScheduleForUser(10L, id);
        MessageScheduleUpdateResponse response = messageScheduleService
                .getByMessageScheduleId(id, "detail");
        model.addAttribute("message", response);
        model.addAttribute("topMessages", topMessages);
        return "layout/messages/message_detail";
    }

    @GetMapping(value = {"/create", "/create/{messageId}"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewCreateMessages(
            Model model,
            @PathVariable(required = false) Long messageId
    ) {
        var groups = groupService.getAll();
        session.setAttribute("groups", groups);

        var request = MessageScheduleDtoRequest.builder().build();

        //copy
        if (messageId != null) {
            request = messageScheduleService.getMessageRequestById(messageId);
        }
        model.addAttribute("message", request);
        return "layout/messages/message_create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createMessages(
            @Valid @ModelAttribute("message") MessageScheduleDtoRequest request,
            BindingResult result,
            Model model
    ) {
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        //save to database
        messageScheduleService.createMessages(request, errors);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "layout/messages/message_create";
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String viewUpdateMessages(Model model, @PathVariable Long id) {
        if (!model.containsAttribute("message")) {
            MessageScheduleUpdateResponse messageScheduleDtoResponse = messageScheduleService
                    .getByMessageScheduleId(id, "id");
            model.addAttribute("message", messageScheduleDtoResponse);
        }

        var groups = groupService.getAll();
        session.setAttribute("groups", groups);
        return "layout/messages/message_update";
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateMessages(
            Model model,
            @Valid @ModelAttribute("message") MessageScheduleDtoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        //check validate
        Map<String, List<String>> errors = new HashMap<>();
        if (result.hasErrors()) {
            validationService.getAllErrors(result, errors);
        }

        //save to database
        messageScheduleService.updateMessages(request, errors);

        if (!errors.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", MessageScheduleMapper.toMessageScheduleUpdateResponse(request));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/messages/update/" + request.getId();
        }

        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }

    @GetMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String changeStatus(
            Model model,
            @PathVariable Long id
    ) {
        boolean rs = messageScheduleService.changeStatus(id);
        String url = (String) session.getAttribute("url");
        return "redirect:" + url;
    }
}
