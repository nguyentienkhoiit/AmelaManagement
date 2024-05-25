package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    List<DepartmentDtoResponse> findAll();

    DepartmentDtoResponse findById(Long id);

    PagingDtoResponse<DepartmentDtoResponse> getAllDepartments(PagingDtoRequest request);

    void changeStatus(Long id);

    void createDepartments(DepartmentDtoRequest request, Map<String, List<String>> errors);

    DepartmentDtoResponse getDepartmentById(Long id);

    void updateDepartments(DepartmentDtoRequest request, Map<String, List<String>> errors);
}
