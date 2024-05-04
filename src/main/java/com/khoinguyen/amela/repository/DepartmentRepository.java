package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByStatusTrue();

    @Query("select d from Department d where d.name = ?1 and d.id != ?2")
    Optional<Department> findByName(String name, Long departmentId);
}
