package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;

public interface AttendanceService {
    boolean checkAttendance();

    public PagingDtoResponse<AttendanceDtoResponse>
    getAttendanceByUserId(PagingDtoRequest pagingDtoRequest, Long userId);
}
