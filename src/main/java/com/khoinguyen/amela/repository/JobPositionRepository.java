package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findByStatusTrue();
}
