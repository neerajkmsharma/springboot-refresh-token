package com.avinya.application.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.avinya.application.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  // private JwtAuthFilter authFilter;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserInfoUserDetailsService();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthFilter authFilter) throws Exception {

    httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher("/h2-console/**"), antMatcher("/products/signUp"),
      antMatcher("/products/login"), antMatcher("/products/refreshToken")));

    httpSecurity.headers(headers -> headers.frameOptions()
      .disable());

    httpSecurity.authorizeHttpRequests(auth -> auth
      .requestMatchers(antMatcher("/h2-console/**"), antMatcher("/products/signUp"), antMatcher("/products/login"),
        antMatcher("/products/refreshToken"))
      .permitAll());

    httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(antMatcher("/products/**"))
      .authenticated());

    httpSecurity.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
