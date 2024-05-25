package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.MessageSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageScheduleRepository extends JpaRepository<MessageSchedule, Long> {
    Optional<MessageSchedule> findByIdAndStatusTrue(Long id);
}
