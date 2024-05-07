package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.UserMessageSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMessageScheduleRepository extends JpaRepository<UserMessageSchedule, Long> {
    List<UserMessageSchedule> findByMessageScheduleId(Long messageScheduleId);
}
