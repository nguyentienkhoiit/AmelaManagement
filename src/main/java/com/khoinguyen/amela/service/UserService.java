package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.paging.PagingDtoResponse;
import com.khoinguyen.amela.model.dto.paging.PagingUserDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoRequest;
import com.khoinguyen.amela.model.dto.profile.ProfileDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoRequest;
import com.khoinguyen.amela.model.dto.user.UserDtoResponse;
import com.khoinguyen.amela.model.dto.user.UserDtoUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    public PagingDtoResponse<UserDtoResponse> getAllUsers(PagingUserDtoRequest pagingDtoRequest);

    void createUser(UserDtoRequest request, Map<String, List<String>> errors);

    ProfileDtoResponse getProfile();

    void updateUser(UserDtoUpdate request, Map<String, List<String>> errors);

    void updateProfile(ProfileDtoRequest request, MultipartFile fileImage, Map<String, List<String>> errors);

    UserDtoResponse getUserById(Long id);

    boolean resetPassword(Long id);


    boolean changeStatus(Long id);

    boolean sendTokenAgain(Long id);
}
