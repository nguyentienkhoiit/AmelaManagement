package com.khoinguyen.amela.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.authentication.ChangePasswordDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.EmailDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

public interface AuthenticationService {
    ServiceResponse<String> submitForgotPassword(EmailDtoRequest request);

    ServiceResponse<String> submitNewPassword(PasswordDtoRequest request);

    ServiceResponse<String> submitCreateNewPassword(PasswordDtoRequest request);

    void submitChangePassword(ChangePasswordDtoRequest request, Map<String, List<String>> errors);

    User loginOauth2(Authentication authentication);
}
