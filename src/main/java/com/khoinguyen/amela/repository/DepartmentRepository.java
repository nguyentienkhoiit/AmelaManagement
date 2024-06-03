package com.khoinguyen.amela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.khoinguyen.amela.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByStatusTrue();

    @Query("select d from Department d where d.name = ?1 and d.id != ?2")
    Optional<Department> findByName(String name, Long departmentId);
}
