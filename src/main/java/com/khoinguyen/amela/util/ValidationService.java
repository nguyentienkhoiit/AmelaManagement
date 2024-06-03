package com.khoinguyen.amela.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class ValidationService {
    public void updateErrors(String key, String value, Map<String, List<String>> errors) {
        errors.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public void getAllErrors(BindingResult result, Map<String, List<String>> errors) {
        for (FieldError error : result.getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage());
        }
    }
}
