package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.ServiceResponse;
import com.khoinguyen.amela.model.dto.user.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest pagingDtoRequest);

    ServiceResponse<String> createUser(UserDtoRequest request);

    ProfileDtoResponse getProfile();

    ServiceResponse<String> updateUser(UserDtoUpdate request);

    ServiceResponse<String> updateProfile(ProfileDtoRequest request, MultipartFile fileImage);

    UserDtoResponse getUserById(Long id);

    boolean resetPassword(Long id);


    boolean changeStatus(Long id);

    boolean sendTokenAgain(Long id);
}
