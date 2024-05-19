package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.AttendanceMapper;
import com.khoinguyen.amela.model.mapper.UserMapper;
import com.khoinguyen.amela.repository.AttendanceRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.AttendanceCriteria;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.util.UserHelper;
import com.khoinguyen.amela.util.ValidationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceServiceImpl implements AttendanceService {
    AttendanceRepository attendanceRepository;
    UserHelper userHelper;
    AttendanceCriteria attendanceCriteria;
    UserRepository userRepository;
    ValidationService validationService;

    @Override
    public PagingDtoResponse<AttendanceDtoResponse> getAttendanceByUserId(PagingDtoRequest pagingDtoRequest, Long userId) {
        return attendanceCriteria.getAttendanceByUserId(pagingDtoRequest, userId);
    }

    @Override
    public boolean changeStatus(Long id) {
        User userLoggedIn = userHelper.getUserLogin();
        var attendanceOptional = attendanceRepository.findById(id);
        if (attendanceOptional.isPresent()) {
            var attendance = attendanceOptional.get();
            attendance.setUpdateAt(LocalDateTime.now());
            attendance.setUpdateBy(userLoggedIn.getId());
            attendance.setStatus(!attendance.isStatus());
            attendanceRepository.save(attendance);
            return true;
        }
        return false;
    }

    @Override
    public void createAttendances(AttendanceDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            validationService.updateErrors("error", "User is not found", errors);
        }

        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(request.getUserId(), request.getCheckDay());
        if (attendanceOptional.isPresent()) {
            validationService.updateErrors("error", "Check day has already been checked in", errors);
        }

        if (!errors.isEmpty()) return;

        Attendance attendance = Attendance.builder()
                .user(userOptional.orElseThrow())
                .checkDay(request.getCheckDay())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .createdAt(LocalDateTime.now())
                .createdBy(userLoggedIn.getId())
                .status(true)
                .updateAt(LocalDateTime.now())
                .updateBy(userLoggedIn.getId())
                .note(request.getNote())
                .build();
        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceDtoUpdateResponse getAttendanceById(Long id) {
        var attendance = attendanceRepository.findById(id).orElse(null);
        if (attendance == null) return null;
        return AttendanceMapper.toAttendanceDtoUpdateResponse(attendance);
    }

    @Override
    public void updateAttendances(AttendanceDtoRequest request, Map<String, List<String>> errors) {
        User userLoggedIn = userHelper.getUserLogin();

        var attendanceOptional = attendanceRepository.findById(request.getAttendanceId());
        if (attendanceOptional.isEmpty()) {
            validationService.updateErrors("error", "Attendance is not found", errors);
        }

        if (!errors.isEmpty()) return;

        Attendance attendance = attendanceOptional.orElseThrow();
        attendance.setUpdateAt(LocalDateTime.now());
        attendance.setUpdateBy(userLoggedIn.getId());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());
        attendance.setNote(request.getNote());
        attendanceRepository.save(attendance);
    }

    @Override
    public boolean checkAttendance() {
        User userLoggedIn = userHelper.getUserLogin();
        log.info("userLoggedIn: {}", UserMapper.toUserDtoResponse(userLoggedIn));
        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(userLoggedIn.getId(), LocalDate.now());
        Attendance attendance;
        if (attendanceOptional.isEmpty()) {
            attendance = Attendance.builder()
                    .checkInTime(LocalTime.now())
                    .checkOutTime(null)
                    .checkDay(LocalDate.now())
                    .user(userLoggedIn)
                    .status(true)
                    .createdBy(userLoggedIn.getId())
                    .updateBy(userLoggedIn.getId())
                    .updateAt(LocalDateTime.now())
                    .build();
        } else {
            attendance = attendanceOptional.orElseThrow();
            attendance.setCheckOutTime(LocalTime.now());
        }
        System.out.println(AttendanceMapper.toAttendanceDtoUpdateResponse(attendance));
        attendanceRepository.save(attendance);
        return true;
    }
}
