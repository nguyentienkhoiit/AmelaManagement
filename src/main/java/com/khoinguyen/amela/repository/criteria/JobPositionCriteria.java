package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.JobPosition;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.position.JobPositionDtoResponse;
import com.khoinguyen.amela.model.mapper.JobPositionMapper;
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
public class JobPositionCriteria {
    EntityManager em;

    public PagingDtoResponse<JobPositionDtoResponse> getAllPositions(PagingDtoRequest request) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select p from JobPosition p");
        if (request.getText() != null) {
            sql.append(" where p.name like :name");
            params.put("name", "%" + request.getText().trim() + "%");
        }
        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select p", "select count(p.id)"));
        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<JobPosition> positionTypedQuery = em.createQuery(sql.toString(), JobPosition.class);
        params.forEach((k, v) -> {
            positionTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        positionTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        positionTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<JobPosition> positions = positionTypedQuery.getResultList();

        long totalPosition = (long) countQuery.getSingleResult();
        long totalPage = (totalPosition / pageSize);
        if (totalPosition % pageSize != 0) {
            totalPage++;
        }

        List<JobPositionDtoResponse> positionDtoResponses = positions.stream()
                .map(JobPositionMapper::toJobPositionDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalPosition, positionDtoResponses);
    }
}
