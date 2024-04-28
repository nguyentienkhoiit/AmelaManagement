package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.repository.AttendanceRepository;
import com.khoinguyen.amela.repository.criteria.AttendanceCriteria;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceServiceImpl implements AttendanceService {
    AttendanceRepository attendanceRepository;
    UserHelper userHelper;
    AttendanceCriteria attendanceCriteria;

    @Override
    public PagingDtoResponse<AttendanceDtoResponse> getAttendanceByUserId(PagingDtoRequest pagingDtoRequest, Long userId) {
        return attendanceCriteria.getAttendanceByUserId(pagingDtoRequest, userId);
    }

    @Override
    public boolean checkAttendance() {
        User userLoggedIn = userHelper.getUserLogin();
        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(userLoggedIn.getId(), LocalDate.now());
        Attendance attendance;
        if (attendanceOptional.isEmpty()) {
            attendance = Attendance.builder()
                    .checkInTime(LocalDateTime.now())
                    .checkOutTime(null)
                    .checkDay(LocalDate.now())
                    .user(userLoggedIn)
                    .status(true)
                    .createdBy(userLoggedIn.getId())
                    .createdAt(LocalDateTime.now())
                    .updateBy(userLoggedIn.getId())
                    .updateAt(LocalDateTime.now())
                    .build();
        }
        else {
            attendance = attendanceOptional.orElseThrow();
            attendance.setCheckOutTime(LocalDateTime.now());
        }
        attendanceRepository.save(attendance);
        return true;
    }
}
