package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.mapper.JobPositionMapper;
import com.khoinguyen.amela.repository.JobPositionRepository;
import com.khoinguyen.amela.repository.criteria.JobPositionCriteria;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPositionServiceImpl implements JobPositionService {
    JobPositionRepository jobPositionRepository;
    JobPositionCriteria jobPositionCriteria;
    UserHelper userHelper;
    OptionalValidator optionalValidator;

    @Override
    public List<JobPositionDtoResponse> findAll() {
        return jobPositionRepository.findByStatusTrue().stream()
                .map(JobPositionMapper::toJobPositionDtoResponse)
                .toList();
    }

    @Override
    public JobPositionDtoResponse findById(long id) {
        return jobPositionRepository.findById(id).map(JobPositionMapper::toJobPositionDtoResponse).orElse(null);
    }

    @Override
    public PagingDtoResponse<JobPositionDtoResponse> getAllPositions(PagingDtoRequest request) {
        return jobPositionCriteria.getAllPositions(request);
    }

    @Override
    public ServiceResponse<String> createPositions(JobPositionDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        //check position name duplicate
        var positionOptional = optionalValidator.findByPositionName(request.getName(), 0L);
        if (positionOptional.isPresent()) {
            response = new ServiceResponse<>(false, "name", "Position name already exists");
            return response;
        }

        JobPosition jobPosition = JobPosition.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(userLoggedIn.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .status(true)
                .build();
        jobPositionRepository.save(jobPosition);

        return response;
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var positionOptional = jobPositionRepository.findById(id);
        if (positionOptional.isPresent()) {
            var position = positionOptional.get();
            position.setUpdateAt(LocalDateTime.now());
            position.setUpdateBy(userLoggedIn.getId());
            position.setStatus(!position.isStatus());
            jobPositionRepository.save(position);
            return true;
        }
        return false;
    }

    @Override
    public JobPositionDtoResponse getPositionById(Long id) {
        Optional<JobPosition> optionalDepartment = jobPositionRepository.findById(id);
        return optionalDepartment.map(JobPositionMapper::toJobPositionDtoResponse).orElse(null);
    }

    @Override
    public ServiceResponse<String> updatePositions(JobPositionDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        JobPosition positionExist = jobPositionRepository.findById(request.getId()).orElse(null);
        if (positionExist == null) {
            response = new ServiceResponse<>(false, "position", "Position not found: " + request.getId());
            return response;
        }

        //check department name duplicate
        var positionOptional = optionalValidator.findByPositionName(request.getName(), request.getId());
        if (positionOptional.isPresent()) {
            response = new ServiceResponse<>(false, "name", "Position name already exists");
            return response;
        }

        JobPosition jobPosition = JobPosition.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(userLoggedIn.getId())
                .updateAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .status(true)
                .build();
        jobPositionRepository.save(jobPosition);

        return response;
    }
}
