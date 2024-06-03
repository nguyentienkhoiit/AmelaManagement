package com.khoinguyen.amela.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khoinguyen.amela.entity.UserGroup;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByUserIdAndGroupId(Long u, Long id);
}
