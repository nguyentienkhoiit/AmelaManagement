package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
    List<JobPosition> findByStatusTrue();

    @Query("select p from JobPosition p where p.name = ?1 and p.id != ?2")
    Optional<JobPosition> findByName(String name, Long positionId);
}
