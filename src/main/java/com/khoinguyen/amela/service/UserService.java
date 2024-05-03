package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;

public interface UserService {
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest pagingDtoRequest);

    ServiceResponse<String> createUser(UserDtoRequest request);

    UserDtoResponse getProfile();

    ServiceResponse<String> updateUser(UserDtoUpdate request);

    void updateProfile(UserDtoRequest request);

    UserDtoResponse getUserById(Long id);

    boolean resetPassword(Long id);


    boolean changeStatus(Long id);
}
