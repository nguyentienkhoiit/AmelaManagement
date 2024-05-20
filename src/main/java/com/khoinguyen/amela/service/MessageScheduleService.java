package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;

import java.util.List;
import java.util.Map;

public interface MessageScheduleService {
    void createMessages(MessageScheduleDtoRequest request, Map<String, List<String>> errors);

    PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesAdmin(PagingDtoRequest pagingDtoRequest);

    MessageScheduleUpdateResponse getByMessageScheduleId(Long id, String type);

    void updateMessages(MessageScheduleDtoRequest request, Map<String, List<String>> errors);

    PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesUser(PagingDtoRequest pagingDtoRequest);

    boolean changeStatus(Long id);

    List<MessageScheduleDtoResponse> getTopMessagesScheduleForUser(Long topElement, Long id);

    MessageScheduleDtoRequest getMessageRequestById(Long messageId);
}
