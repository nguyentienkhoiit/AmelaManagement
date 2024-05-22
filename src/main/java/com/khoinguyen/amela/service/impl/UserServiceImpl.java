package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.configuration.AppConfig;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingUserDtoRequest;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoResponse;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.khoinguyen.amela.util.Constant.PASSWORD_DEFAULT;

@Service
@Slf4j
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
    FileHelper fileHelper;
    ValidationService validationService;
    AppConfig appConfig;

    @Override
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingUserDtoRequest request) {
        return userCriteria.getAllUsers(request);
    }

    private User getUserLatest() {
        return userRepository.findTopByOrderByIdDesc().orElse(null);
    }

    @Transactional
    @Override
    public void createUser(UserDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        if (optionalValidator.findByEmailExist(request.getEmail(), 0L).isPresent()) {
            validationService.updateErrors("email", "Email already exists", errors);
        }

        if (optionalValidator.findByPhoneExist(request.getPhone(), 0L).isPresent()) {
            validationService.updateErrors("phone", "Phone already exists", errors);
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            validationService.updateErrors("dateOfBirth", "Date of birth must be greater than 18", errors);
        }

        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        if (positionOptional.isEmpty()) {
            validationService.updateErrors("position", "Position is not found", errors);
        }

        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        if (departmentOptional.isEmpty()) {
            validationService.updateErrors("department", "Department is not found", errors);
        }

        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        if (roleOptional.isEmpty()) {
            validationService.updateErrors("role", "Role is not found", errors);
        }

        if (!errors.isEmpty()) return;

        String latestCde = getUserLatest() != null ? getUserLatest().getCode() : "000001";

        String code = AttributeGenerator.generateNextUserCode(latestCde);

        User user = UserMapper.toUser(request);
        String username = AttributeGenerator.generatorUsername(user, getUserLatest().getId() + 1);
        user.setCreatedBy(userLoggedIn.getId());
        user.setUsername(username);
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.orElseThrow());
        user.setRole(roleOptional.orElseThrow());
        user.setJobPosition(positionOptional.orElseThrow());
        user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
        user.setCode(code);
        user.setEnabled(false);
        user.setEditUsername(true);
        user.setActivated(false);

        user = userRepository.save(user);

        //Send mail
        sendMailCreateUser(user);
    }

    @Override
    public ProfileDtoResponse getProfile() {
        User userLoggedIn = userHelper.getUserLogin();
        return UserMapper.toProfileDtoResponse(userLoggedIn);
    }

    @Transactional
    @Override
    public void updateProfile(ProfileDtoRequest request, MultipartFile fileImage, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        var userOptionalPhone = optionalValidator.findByPhoneExist(request.getPhone(), userLoggedIn.getId());
        if (userOptionalPhone.isPresent()) {
            validationService.updateErrors("phone", "Phone already exists", errors);
        }

        var userOptionalUsername = optionalValidator.findByUsernameExist(request.getUsername(), userLoggedIn.getId());
        if (userOptionalUsername.isPresent()) {
            validationService.updateErrors("username", "Username already exists", errors);
        }

        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            validationService.updateErrors("dateOfBirth", "Date of birth must be greater than 18", errors);
        }

        if (!fileImage.isEmpty()) {
            try {
                String fileName = fileHelper.uploadFile(fileImage, userLoggedIn);
                if (fileName == null) {
                    validationService.updateErrors("avatar", "Something went wrong", errors);
                }
                userLoggedIn.setAvatar(fileName);
            } catch (IOException e) {
                log.error("Failed to upload file {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }

        if (!errors.isEmpty()) return;

        userLoggedIn.setUpdateBy(userLoggedIn.getId());
        userLoggedIn.setPhone(request.getPhone());
        userLoggedIn.setFirstname(request.getFirstname());
        userLoggedIn.setLastname(request.getLastname());
        userLoggedIn.setGender(request.getGender());
        userLoggedIn.setAddress(request.getAddress());
        userLoggedIn.setDateOfBirth(request.getDateOfBirth());

        if (userLoggedIn.isEditUsername()
                && !request.getUsername().equalsIgnoreCase(userLoggedIn.getUsername())) {
            userLoggedIn.setUsername(request.getUsername());
            userLoggedIn.setEditUsername(false);
        }
        userRepository.save(userLoggedIn);
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
            User user = userOptional.get();
            if (user.getRole().getName().equals(Constant.ADMIN_NAME)) return false;
            user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void updateUser(UserDtoUpdate request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        var userOptional = userRepository.findById(request.getId());
        if (userOptional.isEmpty()) {
            validationService.updateErrors("user", "User is not exists", errors);
        }
        var user = userOptional.orElseThrow();

        if (optionalValidator.findByEmailExist(request.getEmail(), user.getId()).isPresent()) {
            validationService.updateErrors("email", "Email already exists", errors);
        }

        if (optionalValidator.findByPhoneExist(request.getPhone(), user.getId()).isPresent()) {
            validationService.updateErrors("phone", "Phone already exists", errors);
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(DateTimeHelper.parseStringToDate(request.getDateOfBirth()), 18L)) {
            validationService.updateErrors("dateOfBirth", "Date of birth must be greater than 18", errors);
        }

        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        if (positionOptional.isEmpty()) {
            validationService.updateErrors("position", "Position is not found", errors);
        }

        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        if (departmentOptional.isEmpty()) {
            validationService.updateErrors("department", "Department is not found", errors);
        }

        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        if (roleOptional.isEmpty()) {
            validationService.updateErrors("role", "Role is not found", errors);
        }

        if (!errors.isEmpty()) return;

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(DateTimeHelper.parseStringToDate(request.getDateOfBirth()));
        user.setPhone(request.getPhone());
        user.setUpdateAt(LocalDateTime.now());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.orElseThrow());
        user.setRole(roleOptional.orElseThrow());
        user.setJobPosition(positionOptional.orElseThrow());
        userRepository.save(user);
    }

    public ServiceResponse<String> sendMailCreateUser(User user) {
        String token = UUID.randomUUID().toString();
        //send password reset verification email to the user
        String url = appConfig.HOST + "user-new-password?token=" + token;
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
        if (user == null || user.isActivated()) return false;
        ServiceResponse<String> response = sendMailCreateUser(user);
        return true;
    }
}
