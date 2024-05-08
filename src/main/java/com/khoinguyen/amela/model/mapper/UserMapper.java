package com.khoinguyen.amela.model.mapper;

import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;

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
                .status(user.isEnabled())
                .position(JobPositionMapper.toJobPositionDtoResponse(user.getJobPosition()))
                .department(DepartmentMapper.toDepartmentDtoResponse(user.getDepartment()))
                .role(RoleMapper.toRole(user.getRole()))
                .dateOfBirth(user.getDateOfBirth().toString())
                .active(user.isEnabled())
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
                .username(request.getUsername())
                .build();
    }
}
