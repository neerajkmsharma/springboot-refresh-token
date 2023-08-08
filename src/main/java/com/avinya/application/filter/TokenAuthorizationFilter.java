package com.avinya.application.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.avinya.application.config.UserInfoUserDetailsService;
import com.avinya.application.service.TokenAuthorizationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthorizationFilter extends OncePerRequestFilter {

  @Autowired
  private TokenAuthorizationService jwtService;

  @Autowired
  private UserInfoUserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
    final FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      username = jwtService.extractUsername(token);
    }

    if (username != null && SecurityContextHolder.getContext()
      .getAuthentication() == null) {
      final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtService.validateToken(token, userDetails)) {
        final UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
          .setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
