package com.khoinguyen.amela.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.khoinguyen.amela.model.dto.role.RoleDtoResponse;
import com.khoinguyen.amela.model.mapper.RoleMapper;
import com.khoinguyen.amela.repository.RoleRepository;
import com.khoinguyen.amela.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    @Override
    public List<RoleDtoResponse> findAll() {
        return roleRepository.findByStatusTrue().stream()
                .map(RoleMapper::toRole)
                .toList();
    }

    @Override
    public RoleDtoResponse findById(Long id) {
        return roleRepository.findById(id).map(RoleMapper::toRole).orElse(null);
    }
}
