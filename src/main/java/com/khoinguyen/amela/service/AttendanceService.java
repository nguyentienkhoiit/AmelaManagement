package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;

import java.util.List;
import java.util.Map;

public interface AttendanceService {
    boolean checkAttendance();

    PagingDtoResponse<AttendanceDtoResponse>
    getAttendanceByUserId(PagingDtoRequest pagingDtoRequest, Long userId);

    boolean changeStatus(Long id);

    void createAttendances(AttendanceDtoRequest request, Map<String, List<String>> errors);

    AttendanceDtoUpdateResponse getAttendanceById(Long id);

    void updateAttendances(AttendanceDtoRequest request, Map<String, List<String>> errors);
}
