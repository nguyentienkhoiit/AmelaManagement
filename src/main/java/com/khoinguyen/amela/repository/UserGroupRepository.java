package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Optional<UserGroup> findByUserIdAndGroupId(Long u, Long id);
}
