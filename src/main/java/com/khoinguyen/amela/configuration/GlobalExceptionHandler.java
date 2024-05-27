package com.khoinguyen.amela.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(RedirectAttributes redirectAttributes, MaxUploadSizeExceededException e) {
        Map<String, List<String>> errors = new HashMap<>();
        errors.put("avatar", List.of(e.getMessage()));
        redirectAttributes.addFlashAttribute("errors", errors);
        return "redirect:/profile";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(
            RedirectAttributes redirectAttributes,
            AccessDeniedException ex
    ) {
        return "redirect:/forbidden";
    }
}
