package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByStatusTrue();
}
