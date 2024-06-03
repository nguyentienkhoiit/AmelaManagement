package com.khoinguyen.amela.service;

import java.util.List;
import java.util.Map;

import com.khoinguyen.amela.model.dto.group.GroupDtoRequest;
import com.khoinguyen.amela.model.dto.group.GroupDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;

public interface GroupService {
    void createGroups(GroupDtoRequest request, Map<String, List<String>> errors);

    void updateGroups(GroupDtoRequest request, Map<String, List<String>> errors);

    GroupDtoResponse getGroupById(Long id);

    PagingDtoResponse<GroupDtoResponse> getAllGroups(PagingDtoRequest request);

    void changeStatus(Long id);

    List<GroupDtoResponse> getAll();
}
