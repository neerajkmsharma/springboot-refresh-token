package com.avinya.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avinya.application.entity.RefreshToken;
import com.avinya.application.entity.UserInfo;
import com.avinya.application.model.AuthRequest;
import com.avinya.application.model.JwtResponse;
import com.avinya.application.model.Product;
import com.avinya.application.model.RefreshTokenRequest;
import com.avinya.application.service.TokenAuthorizationService;
import com.avinya.application.service.ProductService;
import com.avinya.application.service.RefreshTokenService;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService service;

  @Autowired
  private TokenAuthorizationService jwtService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/signUp")
  public String addNewUser(@RequestBody final UserInfo userInfo) {
    return service.addUser(userInfo);
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public List<Product> getAllTheProducts() {
    return service.getProducts();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Product getProductById(@PathVariable final int id) {
    return service.getProduct(id);
  }

  @PostMapping("/login")
  public JwtResponse authenticateAndGetToken(@RequestBody final AuthRequest authRequest) {
    final Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
    if (authentication.isAuthenticated()) {
      final RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.username());
      return new JwtResponse(jwtService.generateToken(authRequest.username()), refreshToken.getToken());
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
        final String accessToken = jwtService.generateToken(userInfo.getName());
        return new JwtResponse(accessToken, refreshTokenRequest.token());
      })
      .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
  }

}
