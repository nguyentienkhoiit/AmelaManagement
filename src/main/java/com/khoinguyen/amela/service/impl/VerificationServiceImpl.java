package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.entity.Verification;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.repository.VerificationRepository;
import com.khoinguyen.amela.service.VerificationService;
import com.khoinguyen.amela.util.DateTimeHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationServiceImpl implements VerificationService {
    VerificationRepository verificationRepository;

    @Override
    public void createTokenUser(User user, String token) {
        Verification verification = Verification.builder()
                .user(user)
                .token(token)
                .expireAt(DateTimeHelper.getExpirationTime())
                .createdAt(LocalDateTime.now())
                .build();
        verificationRepository.save(verification);
    }

    @Override
    public ServiceResponse<String> validateToken(String token) {
        ServiceResponse<String> response = new ServiceResponse<>(true, "valid", null);

        Optional<Verification> theToken = verificationRepository.findByToken(token);
        if (theToken.isEmpty()) {
            response = new ServiceResponse<>(false, "error", "Token is not found");
            return response;
        }

        if (theToken.get().getExpireAt().isBefore(LocalDateTime.now())) {
            response = new ServiceResponse<>(false, "error", "Token is expired");
            return response;
        }

        return response;
    }
}
