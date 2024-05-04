package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.*;
import com.khoinguyen.amela.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OptionalValidator {
    UserRepository userRepository;
    DepartmentRepository departmentRepository;
    RoleRepository roleRepository;
    JobPositionRepository jobPositionRepository;
    GroupRepository groupRepository;

    public Optional<Group> findByGroupName(String name, Long groupId) {
        return groupRepository.findByName(name, groupId);
    }

    public Optional<Department> findByDepartmentName(String name, Long departmentId) {
        return departmentRepository.findByName(name, departmentId);
    }

    public Optional<JobPosition> findByPositionName(String name, Long positionId) {
        return jobPositionRepository.findByName(name, positionId);
    }

    public Optional<User> findByEmailExist(String email, Long userId) {
        return userRepository.findByEmail(email, userId);
    }

    public Optional<User> findByUsernameExist(String username, Long userId) {
        return userRepository.findByUsername(username, userId);
    }

    public Optional<User> findByPhoneExist(String phone, Long userId) {
        return userRepository.findByPhone(phone, userId);
    }

    public Optional<Department> findByDepartmentId(long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    public Optional<Role> findByRoleId(long roleId) {
        return roleRepository.findById(roleId);
    }

    public Optional<JobPosition> findByJobPositionId(long jobPositionId) {
        return jobPositionRepository.findById(jobPositionId);
    }
}
