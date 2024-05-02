package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.Group;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.GroupMapper;
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
public class GroupCriteria {
    EntityManager em;

    public PagingDtoResponse<GroupDtoResponse> getAllGroups(PagingDtoRequest request) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select g from Group g");
        if (request.getText() != null) {
            sql.append(" where g.name like :name");
            params.put("name", "%" + request.getText().trim() + "%");
        }
        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select g", "select count(g.id)"));
        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<Group> groupTypedQuery = em.createQuery(sql.toString(), Group.class);
        params.forEach((k, v) -> {
            groupTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        groupTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        groupTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<Group> groupList = groupTypedQuery.getResultList();

        long totalGroup = (long) countQuery.getSingleResult();
        long totalPage = (totalGroup / pageSize);
        if (totalGroup % pageSize != 0) {
            totalPage++;
        }

        List<GroupDtoResponse> groupDtoResponses = groupList.stream()
                .map(GroupMapper::toGroupDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalGroup, groupDtoResponses);
    }
}
