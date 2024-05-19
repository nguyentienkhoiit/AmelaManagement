package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;

import java.util.List;
import java.util.Map;

public interface JobPositionService {
    List<JobPositionDtoResponse> findAll();

    JobPositionDtoResponse findById(long id);

    PagingDtoResponse<JobPositionDtoResponse> getAllPositions(PagingDtoRequest request);

    void createPositions(JobPositionDtoRequest request, Map<String, List<String>> errors);

    boolean changeStatus(Long id);

    JobPositionDtoResponse getPositionById(Long id);

    void updatePositions(JobPositionDtoRequest request, Map<String, List<String>> errors);
}
