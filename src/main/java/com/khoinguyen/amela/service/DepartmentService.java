package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.department.DepartmentDtoRequest;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDtoResponse> findAll();

    PagingDtoResponse<DepartmentDtoResponse> getAllGroups(PagingDtoRequest request);

    boolean changeStatus(Long id);

    ServiceResponse<String> createDepartments(DepartmentDtoRequest request);

    DepartmentDtoResponse getDepartmentById(Long id);

    ServiceResponse<String> updateDepartments(DepartmentDtoRequest request);
}
