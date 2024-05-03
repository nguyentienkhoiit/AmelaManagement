package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.name = ?1 and g.id != ?2")
    Optional<Group> findByName(String name, Long groupId);
}
