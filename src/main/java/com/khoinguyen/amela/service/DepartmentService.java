package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDtoResponse> findAll();
}
