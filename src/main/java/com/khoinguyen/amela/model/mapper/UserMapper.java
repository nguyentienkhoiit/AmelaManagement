package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;

import java.time.LocalDateTime;

public class UserMapper {
    public static UserDtoResponse toUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .code(user.getCode())
                .email(user.getEmail())
                .gender(user.getGender())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phone(user.getPhone())
                .status(user.isActivated())
                .position(JobPositionMapper.toJobPositionDtoResponse(user.getJobPosition()))
                .department(DepartmentMapper.toDepartmentDtoResponse(user.getDepartment()))
                .role(RoleMapper.toRole(user.getRole()))
                .dateOfBirth(user.getDateOfBirth().toString())
                .activated(user.isActivated())
                .address(user.getAddress())
                .username(user.getUsername())
                .enabled(user.isEnabled())
                .build();
    }

    public static User toUser(UserDtoRequest request) {
        return User.builder()
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .dateOfBirth(request.getDateOfBirth())
                .lastname(request.getLastname())
                .createdAt(LocalDateTime.now())
                .gender(request.getGender())
                .updateAt(LocalDateTime.now())
                .enabled(true)
                .avatar("/image/avatar.jpg")
                .build();
    }

    public static UserDtoResponse toUserDtoResponse(
            UserDtoUpdate request,
            DepartmentDtoResponse department,
            RoleDtoResponse role,
            JobPositionDtoResponse jobPosition
    ) {
        return UserDtoResponse.builder()
                .id(request.getId())
                .code(request.getCode())
                .email(request.getEmail())
                .gender(request.getGender())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phone(request.getPhone())
                .position(jobPosition)
                .department(department)
                .role(role)
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .username(request.getUsername())
                .build();
    }

    public static ProfileDtoResponse toProfileDtoResponse(User request) {
        return ProfileDtoResponse.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(request.getRole().getName())
                .department(request.getDepartment().getName())
                .position(request.getJobPosition().getName())
                .isEditUsername(request.isEditUsername())
                .code(request.getCode())
                .username(request.getUsername())
                .gender(request.getGender())
                .address(request.getAddress())
                .avatar(request.getAvatar())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth().toString())
                .build();
    }


    public static ProfileDtoResponse toProfileUserDtoResponse(ProfileDtoRequest request, User userLoggedIn) {
        String dateOfBirth = request.getDateOfBirth() == null ? null : request.getDateOfBirth().toString();
        var profileDtoResponse = toProfileDtoResponse(userLoggedIn);
        profileDtoResponse.setUsername(request.getUsername());
        profileDtoResponse.setFirstname(request.getFirstname());
        profileDtoResponse.setLastname(request.getLastname());
        profileDtoResponse.setGender(request.getGender());
        profileDtoResponse.setAddress(request.getAddress());
        profileDtoResponse.setEditUsername(userLoggedIn.isEditUsername());
        profileDtoResponse.setPhone(request.getPhone());
        profileDtoResponse.setDateOfBirth(dateOfBirth);
        profileDtoResponse.setAvatar(request.getAvatar());
        return profileDtoResponse;
    }
}
