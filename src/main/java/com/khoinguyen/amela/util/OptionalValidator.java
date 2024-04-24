package com.khoinguyen.amela.util;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.entity.Role;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.repository.DepartmentRepository;
import com.khoinguyen.amela.repository.JobPositionRepository;
import com.khoinguyen.amela.repository.RoleRepository;
import com.khoinguyen.amela.repository.UserRepository;
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

    public Optional<User> findByEmailExist(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsernameExist(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByPhoneExist(String phone) {
        return userRepository.findByPhone(phone);
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
