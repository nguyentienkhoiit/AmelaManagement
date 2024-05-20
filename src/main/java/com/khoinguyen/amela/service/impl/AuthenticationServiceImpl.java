package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.entity.Verification;
import com.khoinguyen.amela.model.dto.authentication.ChangePasswordDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.EmailDtoRequest;
import com.khoinguyen.amela.model.dto.authentication.PasswordDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.VerificationRepository;
import com.khoinguyen.amela.service.AuthenticationService;
import com.khoinguyen.amela.service.VerificationService;
import com.khoinguyen.amela.util.EmailHandler;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    VerificationService verificationService;
    EmailHandler emailHandler;
    VerificationRepository verificationRepository;
    PasswordEncoder passwordEncoder;
    UserHelper userHelper;
    ValidationService validationService;

    @Transactional
    @Override
    public ServiceResponse<String> submitForgotPassword(EmailDtoRequest request, String rootUrl) {
        ServiceResponse<String> response = new ServiceResponse<>(true, "success",
                "We have just sent a verification link to your email, the link will be expired in 1h");
        User user = userRepository.findByEmailAndEnabledTrue(request.getEmail())
                .orElse(null);
        if (user == null) {
            response = new ServiceResponse<>(false, "error", "No user found with this email");
            return response;
        }
        String token = UUID.randomUUID().toString();
        verificationService.createTokenUser(user, token);
        //send password reset verification email to the user
        String url = rootUrl + "/new-password?token=" + token;
        try {
            emailHandler.sendTokenForgotPassword(user, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            response = new ServiceResponse<>(false, "error", "Something is went wrong");
            return response;
        }
        return response;
    }

    @Transactional
    @Override
    public ServiceResponse<String> submitNewPassword(PasswordDtoRequest request) {
        ServiceResponse<String> response;

        //validate token
        response = verificationService.validateToken(request.getToken());
        if (!response.status()) return response;

        //submit password
        Optional<Verification> theVerification = verificationRepository.findByToken(request.getToken());
        Optional<User> theUser = theVerification.map(Verification::getUser);
        if (theUser.isPresent()) {
            User user = theUser.get();
            String password = passwordEncoder.encode(request.getPassword());
            user.setPassword(password);
            userRepository.save(user);
            verificationRepository.delete(theVerification.get());
            return response;
        }
        return new ServiceResponse<>(false, "error", "Something is went wrong");
    }

    @Transactional
    @Override
    public ServiceResponse<String> submitCreateNewPassword(PasswordDtoRequest request) {
        ServiceResponse<String> response;

        //validate token
        response = verificationService.validateToken(request.getToken());
        if (!response.status()) return response;

        //submit password
        Optional<Verification> theVerification = verificationRepository.findByToken(request.getToken());
        Optional<User> theUser = theVerification.map(Verification::getUser);
        if (theUser.isPresent()) {
            User user = theUser.get();
            String password = passwordEncoder.encode(request.getPassword());
            user.setPassword(password);
            user.setEnabled(true);
            user.setActivated(true);
            userRepository.save(user);
            verificationRepository.delete(theVerification.get());
            return response;
        }
        return new ServiceResponse<>(false, "error", "Something is went wrong");
    }

    @Transactional
    @Override
    public void submitChangePassword(ChangePasswordDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();
        if (!passwordEncoder.matches(request.getOldPassword(), userLoggedIn.getPassword())) {
            validationService.updateErrors("oldPassword", "Password is not correct", errors);
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            validationService.updateErrors("confirmPassword", "Passwords do not match", errors);
        }

        if (!errors.isEmpty()) return;

        //update password
        userLoggedIn.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
        userRepository.save(userLoggedIn);

        //set security context holder
        userHelper.setSecurityContext(request.getConfirmPassword(), null);
    }
}
