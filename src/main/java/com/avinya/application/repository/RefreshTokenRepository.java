package com.avinya.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avinya.application.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

  Optional<RefreshToken> findByToken(String token);
}
