package com.khoinguyen.amela.service;

import java.util.List;
import java.util.Map;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;

public interface JobPositionService {
    List<JobPositionDtoResponse> findAll();

    JobPositionDtoResponse findById(long id);

    PagingDtoResponse<JobPositionDtoResponse> getAllPositions(PagingDtoRequest request);

    void createPositions(JobPositionDtoRequest request, Map<String, List<String>> errors);

    void changeStatus(Long id);

    JobPositionDtoResponse getPositionById(Long id);

    void updatePositions(JobPositionDtoRequest request, Map<String, List<String>> errors);
}
