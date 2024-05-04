package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;

public interface GroupService {
    ServiceResponse<String> createGroups(GroupDtoRequest request);

    ServiceResponse<String> updateGroups(GroupDtoRequest request);

    GroupDtoResponse getGroupById(Long id);

    PagingDtoResponse<GroupDtoResponse> getAllGroups(PagingDtoRequest request);

    boolean changeStatus(Long id);
}
