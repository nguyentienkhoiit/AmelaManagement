package com.khoinguyen.amela.service;

import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;

import java.util.List;

public interface RoleService {
    List<RoleDtoResponse> findAll();

    RoleDtoResponse findById(Long id);
}
