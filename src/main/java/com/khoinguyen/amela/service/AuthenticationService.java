package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.authentication.ChangePasswordDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.EmailDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

import java.util.List;
import java.util.Map;

public interface AuthenticationService {
    ServiceResponse<String> submitForgotPassword(EmailDtoRequest request, String rootUrl);

    ServiceResponse<String> submitNewPassword(PasswordDtoRequest request);

    ServiceResponse<String> submitCreateNewPassword(PasswordDtoRequest request);

    void submitChangePassword(ChangePasswordDtoRequest request, Map<String, List<String>> errors);
}
