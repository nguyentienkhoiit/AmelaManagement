package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.Attendance;
import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.AttendanceMapper;
import com.khoinguyen.amela.util.DateTimeHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AttendanceCriteria {
    EntityManager em;

    public PagingDtoResponse<AttendanceDtoResponse> getAttendanceByUserId(PagingDtoRequest request, Long userId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select a from Attendance a where a.user.id = :userId");
        params.put("userId", userId);

        Map<String, Integer> map = DateTimeHelper.getYearMonthDetail(request.getText());
        if (map != null) {
            sql.append(" and YEAR(a.checkDay) = :year and MONTH(a.checkDay) = :month");
            params.put("year", map.get("year"));
            params.put("month", map.get("month"));
        }

        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select a", "select count(a.id)"));
        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<Attendance> userTypedQuery = em.createQuery(sql.toString(), Attendance.class);
        params.forEach((k, v) -> {
            userTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        userTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        userTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<Attendance> attendances = userTypedQuery.getResultList();

        long totalAttendance = (long) countQuery.getSingleResult();
        long totalPage = (totalAttendance / pageSize);
        if (totalAttendance % pageSize != 0) {
            totalPage++;
        }

        List<AttendanceDtoResponse> attendanceDtoResponses = attendances.stream()
                .map(AttendanceMapper::toAttendanceDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalAttendance, attendanceDtoResponses);
    }
}
