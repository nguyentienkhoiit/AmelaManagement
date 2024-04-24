package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;

public interface UserService {
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest pagingDtoRequest);

    void createUser(UserDtoRequest request);

    UserDtoResponse getProfile();

    void updateProfile(UserDtoRequest request);

    UserDtoResponse getUserById(Long id);
}
