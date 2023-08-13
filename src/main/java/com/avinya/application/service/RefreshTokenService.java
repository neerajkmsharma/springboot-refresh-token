package com.avinya.application.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avinya.application.entity.RefreshToken;
import com.avinya.application.repository.RefreshTokenRepository;
import com.avinya.application.repository.UserInfoRepository;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserInfoRepository userInfoRepository;

  public RefreshToken createRefreshToken(final String username) {
    final RefreshToken refreshToken = RefreshToken.builder()
      .userInfo(userInfoRepository.findByName(username)
        .get())
      .token(UUID.randomUUID()
        .toString())
      .expiryDate(Instant.now()
        .plus(2, ChronoUnit.MINUTES))
      .build();
    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(final String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken verifyExpiration(final RefreshToken token) {
    if (token.getExpiryDate()
      .compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

}
