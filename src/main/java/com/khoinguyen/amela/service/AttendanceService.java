package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoRequest;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

public interface AttendanceService {
    boolean checkAttendance();

    PagingDtoResponse<AttendanceDtoResponse>
    getAttendanceByUserId(PagingDtoRequest pagingDtoRequest, Long userId);

    boolean changeStatus(Long id);

    ServiceResponse<String> createAttendances(AttendanceDtoRequest request);

    AttendanceDtoUpdateResponse getAttendanceById(Long id);

    ServiceResponse<String> updateAttendances(AttendanceDtoRequest request);
}
