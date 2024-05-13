package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.mapper.DepartmentMapper;
import com.khoinguyen.amela.repository.DepartmentRepository;
import com.khoinguyen.amela.repository.criteria.DepartmentCriteria;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentCriteria departmentCriteria;
    UserHelper userHelper;
    OptionalValidator optionalValidator;

    @Override
    public List<DepartmentDtoResponse> findAll() {
        return departmentRepository.findByStatusTrue().stream()
                .map(DepartmentMapper::toDepartmentDtoResponse)
                .toList();
    }

    @Override
    public DepartmentDtoResponse findById(Long id) {
        return departmentRepository.findById(id).map(DepartmentMapper::toDepartmentDtoResponse).orElse(null);
    }

    @Override
    public PagingDtoResponse<DepartmentDtoResponse> getAllGroups(PagingDtoRequest request) {
        return departmentCriteria.getAllDepartments(request);
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            var department = departmentOptional.get();
            department.setUpdateAt(LocalDateTime.now());
            department.setUpdateBy(userLoggedIn.getId());
            department.setStatus(!department.isStatus());
            departmentRepository.save(department);
            return true;
        }
        return false;
    }

    @Override
    public ServiceResponse<String> createDepartments(DepartmentDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        //check department name duplicate
        var departmentOptional = optionalValidator.findByDepartmentName(request.getName(), 0L);
        if (departmentOptional.isPresent()) {
            response = new ServiceResponse<>(false, "name", "Department name already exists");
            return response;
        }

        Department department = Department.builder()
                .createdBy(userLoggedIn.getId())
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .updateAt(LocalDateTime.now())
                .description(request.getDescription())
                .status(true)
                .build();
        departmentRepository.save(department);

        return response;
    }

    @Override
    public DepartmentDtoResponse getDepartmentById(Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.map(DepartmentMapper::toDepartmentDtoResponse).orElse(null);
    }

    @Override
    public ServiceResponse<String> updateDepartments(DepartmentDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        Department departmentExist = departmentRepository.findById(request.getId()).orElse(null);
        if (departmentExist == null) {
            response = new ServiceResponse<>(false, "department", "Department not found: " + request.getId());
            return response;
        }

        //check department name duplicate
        var departmentOptional = optionalValidator.findByDepartmentName(request.getName(), request.getId());
        if (departmentOptional.isPresent()) {
            response = new ServiceResponse<>(false, "name", "Department name already exists");
            return response;
        }

        Department department = Department.builder()
                .id(request.getId())
                .createdBy(userLoggedIn.getId())
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .updateAt(LocalDateTime.now())
                .description(request.getDescription())
                .status(true)
                .build();
        departmentRepository.save(department);

        return response;
    }
}
