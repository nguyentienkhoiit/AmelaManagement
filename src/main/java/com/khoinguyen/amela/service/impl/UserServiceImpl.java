package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
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

//        if (optionalValidator.findByUsernameExist(request.getUsername(), 0L).isPresent()) {
//            response = new ServiceResponse<>(false, "username", "Username already exists");
//            return response;
//        }

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

        String latestCde = getUserLatest() != null ? getUserLatest().getCode() : "000001";

        String code = AttributeGenerator.generateNextUserCode(latestCde);

        User user = UserMapper.toUser(request);
        String username = AttributeGenerator.generatorUsername(user, getUserLatest().getId() + 1);
        user.setCreatedBy(userLoggedIn.getId());
        user.setUsername(username);
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.get());
        user.setRole(roleOptional.get());
        user.setJobPosition(positionOptional.get());
        user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
        user.setCode(code);
        user.setEnabled(false);
        user.setEditUsername(true);
        user.setActivated(false);

        user = userRepository.save(user);

        //Send mail
        response = sendMailCreateUser(user);

        return response;
    }

    @Override
    public ProfileDtoResponse getProfile() {
        User userLoggedIn = userHelper.getUserLogin();
        return UserMapper.toProfileDtoResponse(userLoggedIn);
    }

    @Override
    public ServiceResponse<String> updateProfile(ProfileDtoRequest request, MultipartFile fileImage) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        var userOptionalPhone = optionalValidator.findByPhoneExist(request.getPhone(), userLoggedIn.getId());
        if (userOptionalPhone.isPresent()) {
            response = new ServiceResponse<>(false, "phone", "Phone already existed");
            return response;
        }

        var userOptionalUsername = optionalValidator.findByUsernameExist(request.getUsername(), userLoggedIn.getId());
        if (userOptionalUsername.isPresent()) {
            response = new ServiceResponse<>(false, "username", "Username already existed");
            return response;
        }

        if (DateTimeHelper.compareDateGreaterThan(request.getDateOfBirth(), 18L)) {
            response = new ServiceResponse<>(false, "dateOfBirth", "Date of birth must be greater than 18");
            return response;
        }

        if (!fileImage.isEmpty()) {
            String fileName = null;
            try {
                fileName = fileHelper.uploadFile(fileImage, userLoggedIn);
                if (fileName == null) {
                    response = new ServiceResponse<>(false, "avatar", "Something went wrong");
                    return response;
                }
                log.info("filename: {}", fileName);
                userLoggedIn.setAvatar(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
            User user = userOptional.get();
            if (user.getRole().getName().equals(Constant.ADMIN_NAME)) return false;
            user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
            userRepository.save(user);
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

        if (optionalValidator.findByPhoneExist(request.getPhone(), user.getId()).isPresent()) {
            response = new ServiceResponse<>(false, "phone", "Phone already exists");
            return response;
        }

        //check date of birth is greater than 18 ???
        if (DateTimeHelper.compareDateGreaterThan(DateTimeHelper.parseStringToDate(request.getDateOfBirth()), 18L)) {
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
        user.setDateOfBirth(DateTimeHelper.parseStringToDate(request.getDateOfBirth()));
        user.setPhone(request.getPhone());
        user.setUpdateAt(LocalDateTime.now());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.get());
        user.setRole(roleOptional.get());
        user.setJobPosition(positionOptional.get());
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
