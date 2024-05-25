package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.DepartmentMapper;
import com.khoinguyen.amela.repository.DepartmentRepository;
import com.khoinguyen.amela.repository.criteria.DepartmentCriteria;
import com.khoinguyen.amela.service.DepartmentService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentCriteria departmentCriteria;
    UserHelper userHelper;
    OptionalValidator optionalValidator;
    ValidationService validationService;

    @Override
    public List<DepartmentDtoResponse> findAll() {
        return departmentRepository.findByStatusTrue().stream()
                .map(DepartmentMapper::toDepartmentDtoResponse)
                .toList();
    }

    @Override
    public DepartmentDtoResponse findById(Long id) {
        return departmentRepository.findById(id)
                .map(DepartmentMapper::toDepartmentDtoResponse)
                .orElse(null);
    }

    @Override
    public PagingDtoResponse<DepartmentDtoResponse> getAllDepartments(PagingDtoRequest request) {
        return departmentCriteria.getAllDepartments(request);
    }

    @Override
    public void changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            var department = departmentOptional.get();
            department.setUpdateAt(LocalDateTime.now());
            department.setUpdateBy(userLoggedIn.getId());
            department.setStatus(!department.isStatus());
            departmentRepository.save(department);
        }
    }

    @Transactional
    @Override
    public void createDepartments(DepartmentDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        //check department name duplicate
        optionalValidator
                .findByDepartmentName(request.getName(), 0L)
                .ifPresent(department -> {
                    validationService.updateErrors("name", "Department name already exists", errors);
                });


        if (!errors.isEmpty()) return;

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
    }

    @Override
    public DepartmentDtoResponse getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(DepartmentMapper::toDepartmentDtoResponse)
                .orElse(null);
    }

    @Transactional
    @Override
    public void updateDepartments(DepartmentDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        Department departmentExist = departmentRepository.findById(request.getId()).orElseGet(() -> {
            validationService.updateErrors("department", "Department not found: " + request.getId(), errors);
            return null;
        });

        //check department name duplicate
        optionalValidator.findByDepartmentName(request.getName(), request.getId())
                .ifPresent(department -> {
                    validationService.updateErrors("name", "Department name already exists", errors);
                });

        if (!errors.isEmpty()) return;

        assert departmentExist != null;
        departmentExist.setUpdateBy(userLoggedIn.getId());
        departmentExist.setUpdateAt(LocalDateTime.now());
        departmentExist.setName(request.getName());
        departmentExist.setDescription(request.getDescription());
        departmentRepository.save(departmentExist);
    }
}
