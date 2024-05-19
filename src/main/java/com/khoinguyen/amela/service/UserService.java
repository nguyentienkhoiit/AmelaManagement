package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoRequest;
import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.user.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingDtoRequest pagingDtoRequest);

    void createUser(UserDtoRequest request, Map<String, List<String>> errors);

    ProfileDtoResponse getProfile();

    void updateUser(UserDtoUpdate request, Map<String, List<String>> errors);

    void updateProfile(ProfileDtoRequest request, MultipartFile fileImage, Map<String, List<String>> errors);

    UserDtoResponse getUserById(Long id);

    boolean resetPassword(Long id);


    boolean changeStatus(Long id);

    boolean sendTokenAgain(Long id);
}
