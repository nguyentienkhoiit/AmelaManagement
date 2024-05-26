package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.configuration.AppConfig;
import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.entity.Role;
import com.khoinguyen.amela.entity.User;
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

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

import static com.khoinguyen.amela.util.Constant.FIRST_CODE;
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

    private void validateInput(
            User request,
            Map<String, List<String>> errors,
            Optional<JobPosition> positionOptional,
            Optional<Department> departmentOptional,
            Optional<Role> roleOptional,
            Optional<User> userOptional
    ) {
        Long userId = userOptional.map(User::getId).orElse(0L);

        if (optionalValidator.findByEmailExist(request.getEmail(), userId).isPresent()) {
            validationService.updateErrors("email", "Email already exists", errors);
        }

        if (optionalValidator.findByPhoneExist(request.getPhone(), userId).isPresent()) {
            validationService.updateErrors("phone", "Phone already exists", errors);
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            validationService.updateErrors("dateOfBirth", "Date of birth must be greater than 18", errors);
        }

        if (positionOptional.isEmpty()) {
            validationService.updateErrors("position", "Position is not found", errors);
        }

        if (departmentOptional.isEmpty()) {
            validationService.updateErrors("department", "Department is not found", errors);
        }

        if (roleOptional.isEmpty()) {
            validationService.updateErrors("role", "Role is not found", errors);
        }
    }

    @Transactional
    @Override
    public void createUser(UserDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();
        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        validateInput(
                UserMapper.toUser(request), errors,
                positionOptional, departmentOptional,
                roleOptional, Optional.empty()
        );

        if (!errors.isEmpty()) return;

        String latestCde = getUserLatest() != null ? getUserLatest().getCode() : FIRST_CODE;

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
        userLoggedIn = userRepository.findById(userLoggedIn.getId()).orElseThrow();
        return UserMapper.toProfileDtoResponse(userLoggedIn);
    }

    @Transactional
    @Override
    public void updateProfile(
            ProfileDtoRequest request,
            MultipartFile fileImage,
            Map<String, List<String>> errors
    ) {
        User userLoggedIn = userHelper.getUserLogin();
        User user = userRepository.findById(userLoggedIn.getId()).orElseThrow();

        var userOptionalPhone = optionalValidator.findByPhoneExist(request.getPhone(), user.getId());
        if (userOptionalPhone.isPresent()) {
            validationService.updateErrors("phone", "Phone already exists", errors);
        }

        var userOptionalUsername = optionalValidator.findByUsernameExist(request.getUsername(), user.getId());
        if (userOptionalUsername.isPresent()) {
            validationService.updateErrors("username", "Username already exists", errors);
        }

        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            validationService.updateErrors("dateOfBirth", "Date of birth must be greater than 18", errors);
        }

        if (!fileImage.isEmpty()) {
            String fileName = fileHelper.uploadFile(fileImage, user);
            if (fileName == null) {
                validationService.updateErrors("avatar", "Only JPG or PNG or JPEG uploads are allowed", errors);
            } else user.setAvatar(fileName);
        }

        if (!errors.isEmpty()) return;

        user.setUpdateBy(user.getId());
        user.setPhone(request.getPhone());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());

        if (user.isEditUsername()
                && !request.getUsername().equalsIgnoreCase(user.getUsername())) {
            user.setUsername(request.getUsername());
            user.setEditUsername(false);
        }
        userRepository.save(user);
    }

    @Override
    public UserDtoResponse getUserById(Long id) {
        var userOptional = userRepository.findById(id);
        return userOptional.map(UserMapper::toUserDtoResponse).orElse(null);
    }

    @Override
    public void resetPassword(Long id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole().getName().equals(Constant.ADMIN_NAME)) return;
            user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
            userRepository.save(user);
        }
    }

    @Transactional
    @Override
    public void updateUser(UserDtoUpdate request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        var userOptional = userRepository.findById(request.getId());
        if (userOptional.isEmpty()) {
            validationService.updateErrors("user", "User is not exists", errors);
        }
        var user = userOptional.orElseThrow();
        var roleIdExistDb = user.getRole().getId();

        var positionOptional = optionalValidator.findByJobPositionId(request.getJobPositionId());
        var departmentOptional = optionalValidator.findByDepartmentId(request.getDepartmentId());
        var roleOptional = optionalValidator.findByRoleId(request.getRoleId());
        validateInput(
                UserMapper.toUser(request), errors,
                positionOptional, departmentOptional,
                roleOptional, userOptional
        );

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

        //push session expired
        if (!Objects.equals(request.getRoleId(), roleIdExistDb)) {
            userHelper.pushSessionExpired(user);
        }
    }

    public void sendMailCreateUser(User user) {
        String token = UUID.randomUUID().toString();
        //send password reset verification email to the user
        String url = appConfig.HOST + "user-new-password?token=" + token;
        verificationService.createTokenUser(user, token);
        //send mail
        try {
            emailHandler.sendTokenCreateUser(user, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        new ServiceResponse<>(true, "success", "User created");
    }

    @Override
    public void changeStatus(Long id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
            //push session expired
            userHelper.pushSessionExpired(user);
        }
    }

    @Override
    public void sendTokenAgain(Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null || user.isActivated()) return;
        sendMailCreateUser(user);
    }
}
