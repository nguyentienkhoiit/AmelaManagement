package com.khoinguyen.amela.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.amela.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByStatusTrue();
}
