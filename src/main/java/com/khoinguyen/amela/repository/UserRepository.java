package com.khoinguyen.amela.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.khoinguyen.amela.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = ?1 and u.id != ?2")
    Optional<User> findByEmail(String email, Long userId);

    @Query("select u from User u where u.username = ?1 and u.id != ?2")
    Optional<User> findByUsername(String username, Long userId);

    @Query("select u from User u where u.phone = ?1 and u.id != ?2")
    Optional<User> findByPhone(String phone, Long userId);

    @Query("select u from User u where u.activated = true and u.enabled = true and u.email = ?1")
    Optional<User> findByEmailAndEnabledTrue(String email);

    Optional<User> findTopByOrderByIdDesc();

    @Query("select u from User u where u.username = ?1 or u.email = ?1 and u.activated = true and u.enabled = true")
    Optional<User> findByUsernameOrEmail(String username);

    @Query("select u from User u where u.enabled = true and u.activated = true")
    List<User> findAllUser();

    @Query("select u from User u where u.activated = true and u.enabled = true and u.id = ?1")
    Optional<User> findByIdAndActive(Long id);
}
