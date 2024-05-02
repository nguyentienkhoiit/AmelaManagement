package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.enabled = true and u.email = ?1")
    Optional<User> findByEmail(String email);

    List<User> findByStatusTrue();

    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);
}
