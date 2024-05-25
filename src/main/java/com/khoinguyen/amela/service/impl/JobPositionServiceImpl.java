package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoRequest;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.mapper.JobPositionMapper;
import com.khoinguyen.amela.repository.JobPositionRepository;
import com.khoinguyen.amela.repository.criteria.JobPositionCriteria;
import com.khoinguyen.amela.service.JobPositionService;
import com.khoinguyen.amela.util.OptionalValidator;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPositionServiceImpl implements JobPositionService {
    JobPositionRepository jobPositionRepository;
    JobPositionCriteria jobPositionCriteria;
    UserHelper userHelper;
    OptionalValidator optionalValidator;
    ValidationService validationService;

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

    @Transactional
    @Override
    public void createPositions(JobPositionDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        //check position name duplicate
        var positionOptional = optionalValidator.findByPositionName(request.getName(), 0L);
        if (positionOptional.isPresent()) {
            validationService.updateErrors("name", "Position name already exists", errors);
        }

        if (!errors.isEmpty()) return;

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
    }

    @Override
    public void changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var positionOptional = jobPositionRepository.findById(id);
        if (positionOptional.isPresent()) {
            var position = positionOptional.get();
            position.setUpdateAt(LocalDateTime.now());
            position.setUpdateBy(userLoggedIn.getId());
            position.setStatus(!position.isStatus());
            jobPositionRepository.save(position);
        }
    }

    @Override
    public JobPositionDtoResponse getPositionById(Long id) {
        return jobPositionRepository.findById(id)
                .map(JobPositionMapper::toJobPositionDtoResponse)
                .orElse(null);
    }

    @Transactional
    @Override
    public void updatePositions(JobPositionDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        JobPosition positionExist = jobPositionRepository.findById(request.getId()).orElse(null);
        if (positionExist == null) {
            validationService.updateErrors("position", "Position not found: " + request.getId(), errors);
        }

        //check department name duplicate
        var positionOptional = optionalValidator.findByPositionName(request.getName(), request.getId());
        if (positionOptional.isPresent()) {
            validationService.updateErrors("name", "Position name already exists", errors);
        }

        if (!errors.isEmpty()) return;

        assert positionExist != null;
        positionExist.setUpdateAt(LocalDateTime.now());
        positionExist.setUpdateBy(userLoggedIn.getId());
        positionExist.setName(request.getName());
        positionExist.setDescription(request.getDescription());
        jobPositionRepository.save(positionExist);
    }
}
