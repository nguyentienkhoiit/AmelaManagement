package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;

import java.util.List;

public interface JobPositionService {
    List<JobPositionDtoResponse> findAll();

    PagingDtoResponse<JobPositionDtoResponse> getAllPositions(PagingDtoRequest request);

    ServiceResponse<String> createPositions(JobPositionDtoRequest request);

    boolean changeStatus(Long id);

    JobPositionDtoResponse getPositionById(Long id);

    ServiceResponse<String> updatePositions(JobPositionDtoRequest request);
}
