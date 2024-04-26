package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("select a from Attendance a where a.user.id = :userId and a.checkDay = :checkDay")
    Optional<Attendance> findAttendanceByUserAndCheckDay(Long userId, LocalDate checkDay);
}
