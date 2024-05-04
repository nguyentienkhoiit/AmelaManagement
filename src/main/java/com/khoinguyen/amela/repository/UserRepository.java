package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.id != ?2")
    Optional<User> findByEmail(String email, Long userId);

    @Query("select u from User u where u.username = ?1 and u.id != ?2")
    Optional<User> findByUsername(String username, Long userId);

    @Query("select u from User u where u.phone = ?1 and u.id != ?2")
    Optional<User> findByPhone(String phone, Long userId);
}
