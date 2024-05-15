package com.khoinguyen.amela.repository.criteria;

import com.khoinguyen.amela.entity.MessageSchedule;
import com.khoinguyen.amela.entity.User;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.mapper.MessageScheduleMapper;
import com.khoinguyen.amela.util.Constant;
import com.khoinguyen.amela.util.UserHelper;
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

import static com.khoinguyen.amela.util.Constant.PAGE_SIZE_MESSAGE_USER;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageScheduleCriteria {
    EntityManager em;
    UserHelper userHelper;

    public PagingDtoResponse<MessageScheduleDtoResponse> getAllMessages(PagingDtoRequest request) {
        User userLoggedIn = userHelper.getUserLogin();
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder();

        String roleName = userLoggedIn.getRole().getName();
        if (roleName.equals(Constant.ADMIN_NAME)) {
            sql = new StringBuilder("select ms from MessageSchedule ms " +
                    "where ms.senderName like :sender or ms.subject like :subject order by ms.publishAt desc");
        } else if (roleName.equals(Constant.USER_NAME)) {
            sql = new StringBuilder("select ms from MessageSchedule ms where " +
                    "ms.publishAt < current_timestamp() and ms.status = true and " +
                    "(ms.id in (select ums.messageSchedule.id from UserMessageSchedule ums where " +
                    "ums.user.id = :userId and (ms.senderName like :sender or ms.subject like :subject) ) or " +
                    "ms.group.id in (select g.id from Group g join UserGroup ug on g.id = ug.group.id where " +
                    "ug.user.id = :userId and g.status = true and (ms.senderName like :sender or ms.subject like :subject) )) " +
                    "order by ms.publishAt desc");
            params.put("userId", userLoggedIn.getId());
        }
        params.put("sender", "%%");
        params.put("subject", "%%");

        if (request.getText() != null) {
            params.put("sender", "%" + request.getText().trim() + "%");
            params.put("subject", "%" + request.getText().trim() + "%");
        }

        //filter search
        Query countQuery = em.createQuery(sql.toString()
                .replace("select ms", "select count(ms.id)"));

        long pageIndex = request.getPageIndex();
        long pageSize = roleName.equals(Constant.USER_NAME) ? PAGE_SIZE_MESSAGE_USER : request.getPageSize();

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
