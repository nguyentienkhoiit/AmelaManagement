package com.khoinguyen.amela.service;

import java.util.List;

import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;

public interface RoleService {
    List<RoleDtoResponse> findAll();

    RoleDtoResponse findById(Long id);
}
