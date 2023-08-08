package com.avinya.application.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenAuthorizationService {

  public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(final String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSignKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Boolean isTokenExpired(final String token) {
    return extractExpiration(token).before(new Date());
  }

  public boolean validateToken(final String token, final UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String generateToken(final String userName) {
    final Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  private String createToken(final Map<String, Object> claims, final String userName) {
    return Jwts.builder()
      .setClaims(claims)
      .setSubject(userName)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
      .signWith(getSignKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private Key getSignKey() {
    final byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
