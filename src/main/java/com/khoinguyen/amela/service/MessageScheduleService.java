package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoRequest;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleDtoResponse;
import com.khoinguyen.amela.model.dto.messages.MessageScheduleUpdateResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

import java.util.List;

public interface MessageScheduleService {
    ServiceResponse<String> createMessages(MessageScheduleDtoRequest request);

    PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesAdmin(PagingDtoRequest pagingDtoRequest);

    MessageScheduleUpdateResponse getByMessageScheduleId(Long id, String type);

    ServiceResponse<String> updateMessages(MessageScheduleDtoRequest request);

    PagingDtoResponse<MessageScheduleDtoResponse> getAllMessagesUser(PagingDtoRequest pagingDtoRequest);

    boolean changeStatus(Long id);

    List<MessageScheduleDtoResponse> getTopMessagesScheduleForUser(Long topElement, Long id);

    MessageScheduleDtoRequest getMessageRequestById(Long messageId);
}
