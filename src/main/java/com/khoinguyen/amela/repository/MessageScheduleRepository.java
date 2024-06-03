package com.khoinguyen.amela.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.amela.entity.MessageSchedule;

public interface MessageScheduleRepository extends JpaRepository<MessageSchedule, Long> {
    Optional<MessageSchedule> findByIdAndStatusTrue(Long id);
}
