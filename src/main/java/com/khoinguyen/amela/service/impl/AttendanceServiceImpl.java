package com.khoinguyen.amela.service.impl;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.mapper.AttendanceMapper;
import com.khoinguyen.amela.repository.AttendanceRepository;
import com.khoinguyen.amela.repository.UserRepository;
import com.khoinguyen.amela.repository.criteria.AttendanceCriteria;
import com.khoinguyen.amela.service.AttendanceService;
import com.khoinguyen.amela.util.DateTimeHelper;
import com.khoinguyen.amela.util.UserHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttendanceServiceImpl implements AttendanceService {
    AttendanceRepository attendanceRepository;
    UserHelper userHelper;
    AttendanceCriteria attendanceCriteria;
    UserRepository userRepository;

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
    public ServiceResponse<String> createAttendances(AttendanceDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        //check in no more than 3 days from current time
        if (DateTimeHelper.isExpiredDay(request.getCheckDay(), LocalDate.now(), 3)) {
            response = new ServiceResponse<>(false, "error", "Attendance addition overdue");
            return response;
        }

        if (request.getCheckInTime().isAfter(request.getCheckOutTime())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out time must be after check in day");
            return response;
        }

        if (request.getCheckDay().isAfter(LocalDate.now())) {
            response = new ServiceResponse<>(false, "checkDay", "Check day cannot exceed the current time is " + LocalDate.now());
            return response;
        }

        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "error", "User is not found");
            return response;
        }

        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(request.getUserId(), request.getCheckDay());
        if (attendanceOptional.isPresent()) {
            response = new ServiceResponse<>(false, "error", "Check time already exists");
            return response;
        }

        Attendance attendance = Attendance.builder()
                .user(userOptional.get())
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
        return response;
    }

    @Override
    public AttendanceDtoUpdateResponse getAttendanceById(Long id) {
        var attendance = attendanceRepository.findById(id).orElse(null);
        if (attendance == null) return null;
        return AttendanceMapper.toAttendanceDtoUpdateResponse(attendance);
    }

    @Override
    public ServiceResponse<String> updateAttendances(AttendanceDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        ServiceResponse<String> response = new ServiceResponse<>(true, "none", null);

        var attendanceOptional = attendanceRepository.findById(request.getAttendanceId());
        if (attendanceOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "error", "Attendance is not found");
            return response;
        }

        Attendance attendance = attendanceOptional.get();
        //check in no more than 3 days from current time
        if (DateTimeHelper.isExpiredDay(attendance.getCheckDay(), LocalDate.now(), 3)) {
            response = new ServiceResponse<>(false, "error", "Attendance editing overdue");
            return response;
        }

        if (request.getCheckInTime().isAfter(request.getCheckOutTime())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out time must be after check in day");
            return response;
        }

        attendance.setUpdateAt(LocalDateTime.now());
        attendance.setUpdateBy(userLoggedIn.getId());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());
        attendance.setNote(request.getNote());
        attendanceRepository.save(attendance);

        return response;
    }

    @Override
    public boolean checkAttendance() {
        User userLoggedIn = userHelper.getUserLogin();
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
        attendanceRepository.save(attendance);
        return true;
    }
}
