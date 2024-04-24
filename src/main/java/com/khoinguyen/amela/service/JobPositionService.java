package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;

import java.util.List;

public interface JobPositionService {
    List<JobPositionDtoResponse> findAll();
}
