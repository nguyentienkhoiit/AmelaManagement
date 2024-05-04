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

        if (!request.getCheckInTime().toLocalDate().isEqual(request.getCheckOutTime().toLocalDate())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out day must be equal check in day");
            return response;
        }

        if (request.getCheckInTime().isAfter(request.getCheckOutTime())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out time must be after check in day");
            return response;
        }

        LocalDate date = request.getCheckInTime().toLocalDate();
        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(request.getUserId(), date);
        if (attendanceOptional.isPresent()) {
            response = new ServiceResponse<>(false, "error", "Check time already exists");
            return response;
        }

        //check in no more than 3 days from current time
        if (DateTimeHelper.isExpiredDay(request.getCheckInTime(), LocalDateTime.now(), 3)) {
            response = new ServiceResponse<>(false, "error", "Over date adding");
            return response;
        }

        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "user", "User is not found");
            return response;
        }

        Attendance attendance = Attendance.builder()
                .user(userOptional.get())
                .checkDay(date)
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
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

        //day of check in equal day of checkout
        if (!request.getCheckInTime().toLocalDate().isEqual(request.getCheckOutTime().toLocalDate())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out day must be equal check in day");
            return response;
        }

        //time of checkin < time of checkout
        if (request.getCheckInTime().isAfter(request.getCheckOutTime())) {
            response = new ServiceResponse<>(false, "checkOutTime", "Check out time must be after check in day");
            return response;
        }

        //check attendances exist
        LocalDate date = request.getCheckInTime().toLocalDate();
        Optional<Attendance> attendanceOptional = attendanceRepository
                .findAttendanceByUserAndCheckDay(request.getUserId(), date);
        if (attendanceOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "error", "Check time is not exists");
            return response;
        }

        //check in no more than 3 days from current time
        if (DateTimeHelper.isExpiredDay(attendanceOptional.get().getCheckInTime(), LocalDateTime.now(), 3)) {
            response = new ServiceResponse<>(false, "error", "Over date updating");
            return response;
        }


        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            response = new ServiceResponse<>(false, "error", "User is not found");
            return response;
        }

        Attendance attendance = Attendance.builder()
                .id(request.getAttendanceId())
                .user(userOptional.get())
                .checkDay(date)
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
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
                    .updateBy(userLoggedIn.getId())
                    .updateAt(LocalDateTime.now())
                    .build();
        } else {
            attendance = attendanceOptional.orElseThrow();
            attendance.setCheckOutTime(LocalDateTime.now());
        }
        attendanceRepository.save(attendance);
        return true;
    }
}
