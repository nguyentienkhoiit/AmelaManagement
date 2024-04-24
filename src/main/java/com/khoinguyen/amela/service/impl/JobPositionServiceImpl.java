package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.mapper.JobPositionMapper;
import com.khoinguyen.amela.repository.JobPositionRepository;
import com.khoinguyen.amela.service.JobPositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPositionServiceImpl implements JobPositionService {
    JobPositionRepository jobPositionRepository;

    @Override
    public List<JobPositionDtoResponse> findAll() {
        return jobPositionRepository.findByStatusTrue().stream()
                .map(JobPositionMapper::toJobPosition)
                .toList();
    }
}
