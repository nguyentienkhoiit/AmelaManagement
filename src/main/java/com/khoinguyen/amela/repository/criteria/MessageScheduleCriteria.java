package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.MessageScheduleMapper;
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
public class MessageScheduleCriteria {
    EntityManager em;

    public PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesAdmin(PagingDtoRequest request) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select m from MessageSchedule m");
        if (request.getText() != null) {
            sql.append(" where m.senderName like :sender or m.subject like :subject");
            params.put("sender", "%" + request.getText().trim() + "%");
            params.put("subject", "%" + request.getText().trim() + "%");
        }
        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select m", "select count(m.id)"));
        long pageIndex = request.getPageIndex();
        long pageSize = request.getPageSize();

        TypedQuery<MessageSchedule> messageScheduleTypedQuery = em.createQuery(sql.toString(), MessageSchedule.class);
        params.forEach((k, v) -> {
            messageScheduleTypedQuery.setParameter(k, v);
            countQuery.setParameter(k, v);
        });

        //paging
        messageScheduleTypedQuery.setFirstResult((int) ((pageIndex - 1) * pageSize));
        messageScheduleTypedQuery.setMaxResults(Math.toIntExact(pageSize));
        List<MessageSchedule> messageScheduleList = messageScheduleTypedQuery.getResultList();

        long totalMessageSchedule = (long) countQuery.getSingleResult();
        long totalPage = (totalMessageSchedule / pageSize);
        if (totalMessageSchedule % pageSize != 0) {
            totalPage++;
        }

        List<MessageScheduleDtoResponse> messageScheduleDtoResponses = messageScheduleList.stream()
                .map(MessageScheduleMapper::toMessageScheduleDtoResponse)
                .toList();
        return new PagingDtoResponse<>(totalPage, totalMessageSchedule, messageScheduleDtoResponses);
    }
}
