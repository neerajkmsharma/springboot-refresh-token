package com.avinya.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avinya.application.entity.RefreshToken;
import com.avinya.application.entity.UserInfo;
import com.avinya.application.model.AuthRequest;
import com.avinya.application.model.JwtResponse;
import com.avinya.application.model.RefreshTokenRequest;
import com.avinya.application.service.RefreshTokenService;
import com.avinya.application.service.TokenAuthorizationService;
import com.avinya.application.service.UserInfoService;

@RestController
@RequestMapping("/authorization")
public class TokenAuthorizationController {

  @Autowired
  private TokenAuthorizationService authorizationService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserInfoService userInfoService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/signUp")
  public String addNewUser(@RequestBody final UserInfo userInfo) {
    return userInfoService.addUser(userInfo);
  }

  @PostMapping("/auth-token")
  public JwtResponse authenticateAndGetToken(@RequestBody final AuthRequest authRequest) {
    final Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
    if (authentication.isAuthenticated()) {
      final RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.username());
      return new JwtResponse(authorizationService.generateToken(authRequest.username()), refreshToken.getToken());
    }
    else {
      throw new UsernameNotFoundException("invalid user request !");
    }
  }

  @PostMapping("/refreshToken")
  public JwtResponse refreshToken(@RequestBody final RefreshTokenRequest refreshTokenRequest) {
    return refreshTokenService.findByToken(refreshTokenRequest.token())
      .map(refreshTokenService::verifyExpiration)
      .map(RefreshToken::getUserInfo)
      .map(userInfo -> {
        final String accessToken = authorizationService.generateToken(userInfo.getName());
        return new JwtResponse(accessToken, refreshTokenRequest.token());
      })
      .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
  }

}
