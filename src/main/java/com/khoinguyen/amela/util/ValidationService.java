package com.khoinguyen.amela.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationService {
    public static void main(String[] args) {
        ValidationService errorManager = new ValidationService();
        Map<String, List<String>> errors = new HashMap<>();

        // Adding errors to the "username" field
        errorManager.updateErrors("username", "Username cannot be blank", errors);
        errorManager.updateErrors("username", "Username must be between 3 and 20 characters", errors);

        // Adding an error to the "password" field
        errorManager.updateErrors("password", "Password must be at least 8 characters", errors);

        // Print the errors
        errors.forEach((key, value) -> {
            System.out.println("Field: " + key);
            value.forEach(error -> System.out.println(" - Error: " + error));
        });
    }

    public void updateErrors(String key, String value, Map<String, List<String>> errors) {
        errors.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    public void getAllErrors(BindingResult result, Map<String, List<String>> errors) {
        for (FieldError error : result.getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), k -> new ArrayList<>()).add(error.getDefaultMessage());
        }
    }
}
