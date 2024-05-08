package com.khoinguyen.amela.repository;

import com.khoinguyen.amela.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    @Query("select t from Verification t where t.token = ?1")
    public Optional<Verification> findByToken(String token);
}
