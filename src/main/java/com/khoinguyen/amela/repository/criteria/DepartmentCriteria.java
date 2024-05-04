package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.Department;
import com.khoinguyen.amela.model.dto.department.DepartmentDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.DepartmentMapper;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentCriteria {
    EntityManager em;

    public PagingDtoResponse<DepartmentDtoResponse> getAllDepartments(PagingDtoRequest request) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select d from Department d");
        if (request.getText() != null) {
            sql.append(" where d.name like :name");
            params.put("name", "%" + request.getText().trim() + "%");
        }
        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select d", "select count(d.id)"));
        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<Department> departmentTypedQuery = em.createQuery(sql.toString(), Department.class);
        params.forEach((k, v) -> {
            departmentTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        departmentTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        departmentTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<Department> departments = departmentTypedQuery.getResultList();

        long totalDepartment = (long) countQuery.getSingleResult();
        long totalPage = (totalDepartment / pageSize);
        if (totalDepartment % pageSize != 0) {
            totalPage++;
        }

        List<DepartmentDtoResponse> departmentDtoResponses = departments.stream()
                .map(DepartmentMapper::toDepartmentDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalDepartment, departmentDtoResponses);
    }
}
