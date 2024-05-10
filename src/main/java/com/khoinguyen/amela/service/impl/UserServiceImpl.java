package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.UserCriteria;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.service.VerificationService;
import com.khoinguyen.amela.util.*;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.khoinguyen.amela.util.Constant.PASSWORD_DEFAULT;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserCriteria userCriteria;
    UserHelper userHelper;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    OptionalValidator optionalValidator;
    VerificationService verificationService;
    EmailHandler emailHandler;

    @Override
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest request) {
        return userCriteria.getAllUsers(request);
    }

    private User getUserLatest() {
        return userRepository.findTopByOrderByIdDesc().orElse(null);
    }

    @Override
    public ServiceResponse<String> createUser(UserDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        if (optionalValidator.findByEmailExist(request.getEmail(), 0L).isPresent()) {
            response = new ServiceResponse<>(false, "email", "Email already exists");
            return response;
        }

        if (optionalValidator.findByUsernameExist(request.getUsername(), 0L).isPresent()) {
            response = new ServiceResponse<>(false, "username", "Username already exists");
            return response;
        }

        if (optionalValidator.findByPhoneExist(request.getPhone(), 0L).isPresent()) {
            response = new ServiceResponse<>(false, "phone", "Phone already exists");
            return response;
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            response = new ServiceResponse<>(false, "dateOfBirth", "Date of birth must be greater than 18");
            return response;
        }

        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        if (positionOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "position", "Position is not found");
            return response;
        }

        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        if (departmentOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "department", "Department is not found");
            return response;
        }

        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        if (roleOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "role", "Role is not found");
            return response;
        }

        String code = CodeGenerator.generateNextUserCode(getUserLatest().getCode());
        User user = UserMapper.toUser(request);
        user.setCreatedBy(userLoggedIn.getId());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.get());
        user.setRole(roleOptional.get());
        user.setJobPosition(positionOptional.get());
        user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
        user.setCode(code);
        user.setEnabled(false);
        user.setActivated(false);

        user = userRepository.save(user);

        //Send mail
        response = sendMailCreateUser(user);

        return response;
    }

    @Override
    public UserDtoResponse getProfile() {
        User userLoggedIn = userHelper.getUserLogin();
        return UserMapper.toUserDtoResponse(userLoggedIn);
    }

    @Override
    public ServiceResponse<String> updateProfile(UserDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        var userOptional = optionalValidator.findByPhoneExist(request.getPhone(), userLoggedIn.getId());
        if (userOptional.isPresent()) {
            response = new ServiceResponse<>(false, "phone", "Phone already existed");
            return response;
        }

        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            response = new ServiceResponse<>(false, "dateOfBirth", "Date of birth must be greater than 18");
            return response;
        }

        userLoggedIn.setUpdateBy(userLoggedIn.getId());
        userLoggedIn.setPhone(request.getPhone());
        userLoggedIn.setFirstname(request.getFirstname());
        userLoggedIn.setLastname(request.getLastname());
        userLoggedIn.setGender(request.getGender());
        userLoggedIn.setAddress(request.getAddress());
        userLoggedIn.setDateOfBirth(request.getDateOfBirth());
        userRepository.save(userLoggedIn);

        return response;
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        var userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::toUserDtoResponse).orElse(null);
    }

    @Override
    public boolean resetPassword(Long id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
            return true;
        }
        return false;
    }

    @Override
    public ServiceResponse<String> updateUser(UserDtoUpdate request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        var userOptional = userRepository.findById(request.getId());
        if (userOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "id", "User is not found");
            return response;
        }
        var user = userOptional.get();

        if (optionalValidator.findByEmailExist(request.getEmail(), user.getId()).isPresent()) {
            response = new ServiceResponse<>(false, "email", "Email already exists");
            return response;
        }

        if (optionalValidator.findByUsernameExist(request.getUsername(), user.getId()).isPresent()) {
            response = new ServiceResponse<>(false, "username", "Username already exists");
            return response;
        }

        if (optionalValidator.findByPhoneExist(request.getPhone(), user.getId()).isPresent()) {
            response = new ServiceResponse<>(false, "phone", "Phone already exists");
            return response;
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            response = new ServiceResponse<>(false, "dateOfBirth", "Date of birth must be greater than 18");
            return response;
        }

        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        if (positionOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "position", "Position is not found");
            return response;
        }

        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        if (departmentOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "department", "Department is not found");
            return response;
        }

        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        if (roleOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "role", "Role is not found");
            return response;
        }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhone(request.getPhone());
        user.setUsername(request.getUsername());
        user.setUpdateAt(LocalDateTime.now());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.get());
        user.setRole(roleOptional.get());
        user.setJobPosition(positionOptional.get());
        user.setEnabled(false);
        userRepository.save(user);

        return response;
    }

    public ServiceResponse<String> sendMailCreateUser(User user) {
        String token = UUID.randomUUID().toString();
        //send password reset verification email to the user
        String url = Constant.HOST + "user-new-password?token=" + token;
        verificationService.createTokenUser(user, token);
        //send mail
        try {
            emailHandler.sendTokenCreateUser(user, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return new ServiceResponse<>(false, "error", "Something is went wrong");
        }
        return new ServiceResponse<>(true, "success", "User created");
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setUpdateAt(LocalDateTime.now());
            user.setUpdateBy(userLoggedIn.getId());
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendTokenAgain(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) return false;
        ServiceResponse<String> response = sendMailCreateUser(user);
        return true;
    }
}
