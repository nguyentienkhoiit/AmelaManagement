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
import com.khoinguyen.amela.util.CodeGenerator;
import com.khoinguyen.amela.util.DateTimeHelper;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest request) {
        return userCriteria.getAllUsers(request);
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

        User user = UserMapper.toUser(request);
        user.setCreatedBy(userLoggedIn.getId());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentOptional.get());
        user.setRole(roleOptional.get());
        user.setJobPosition(positionOptional.get());
        user.setPassword(passwordEncoder.encode(PASSWORD_DEFAULT));
        user.setCode(CodeGenerator.generateCode());
        userRepository.save(user);

        return response;
    }

    @Override
    public UserDtoResponse getProfile() {
        User userLoggedIn = userHelper.getUserLogin();
        return UserMapper.toUserDtoResponse(userLoggedIn);
    }

    @Override
    public void updateProfile(UserDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        userLoggedIn.setUpdateBy(userLoggedIn.getId());
        userLoggedIn.setPhone(request.getPhone());
        userLoggedIn.setFirstname(request.getFirstname());
        userLoggedIn.setLastname(request.getLastname());
        userLoggedIn.setGender(request.getGender());
        userLoggedIn.setAddress(request.getAddress());
        userLoggedIn.setDateOfBirth(request.getDateOfBirth());
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
        userRepository.save(user);

        return response;
    }

    @Override
    public boolean changeStatus(Long id) {
        var userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            user.setUpdateAt(LocalDateTime.now());
            user.setUpdateBy(user.getId());
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
