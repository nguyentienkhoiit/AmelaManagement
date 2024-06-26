package com.khoinguyen.amela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.khoinguyen.amela.entity.UserMessageSchedule;

public interface UserMessageScheduleRepository extends JpaRepository<UserMessageSchedule, Long> {

    @Query("select u from UserMessageSchedule u where u.messageSchedule.id = ?1")
    List<UserMessageSchedule> findByMessageScheduleId(Long messageScheduleId);

    @Modifying
    @Query("DELETE FROM UserMessageSchedule u WHERE u.messageSchedule.id = :messageScheduleId")
    void deleteByMessageScheduleId(@Param("messageScheduleId") Long messageScheduleId);
}
