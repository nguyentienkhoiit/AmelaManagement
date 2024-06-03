package com.khoinguyen.amela.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.khoinguyen.amela.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select a from Attendance a where a.user.id = ?1 and a.checkDay = ?2")
    Optional<Attendance> findAttendanceByUserAndCheckDay(Long userId, LocalDate checkDay);
}
