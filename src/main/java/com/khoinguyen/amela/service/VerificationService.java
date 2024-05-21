package com.khoinguyen.amela.service;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

public interface VerificationService {
    void createTokenUser(User user, String token);

    ServiceResponse<String> validateToken(String token, boolean isCreated);
}
