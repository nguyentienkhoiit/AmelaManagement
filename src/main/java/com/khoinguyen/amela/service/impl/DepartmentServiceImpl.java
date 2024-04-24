package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.mapper.DepartmentMapper;
import com.khoinguyen.amela.repository.DepartmentRepository;
import com.khoinguyen.amela.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDtoResponse> findAll() {
        return departmentRepository.findByStatusTrue().stream()
                .map(DepartmentMapper::toDepartment)
                .toList();
    }
}
