package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.repository.DepartmentRepository;
import com.khoinguyen.amela.repository.JobPositionRepository;
import com.khoinguyen.amela.repository.RoleRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.UserCriteria;
import com.khoinguyen.amela.service.UserService;
import com.khoinguyen.amela.util.CodeGenerator;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserCriteria userCriteria;
    UserHelper userHelper;
    DepartmentRepository departmentRepository;
    RoleRepository roleRepository;
    JobPositionRepository positionRepository;
    PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest request) {
        return userCriteria.getAllUsers(request);
    }

    @Override
    public void createUser(UserDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();

        User user = UserMapper.toUser(request);
        user.setCreatedBy(userLoggedIn.getId());
        user.setUpdateBy(userLoggedIn.getId());
        user.setDepartment(departmentRepository.findById(request.getDepartmentId()).orElseThrow());
        user.setRole(roleRepository.findById(request.getRoleId()).orElseThrow());
        user.setJobPosition(positionRepository.findById(request.getJobPositionId()).orElseThrow());
        user.setPassword(passwordEncoder.encode(Constant.PASSWORD_DEFAULT));
        user.setCode(CodeGenerator.generateCode());
        userRepository.save(user);
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
}
