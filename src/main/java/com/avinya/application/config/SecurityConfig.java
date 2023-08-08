package com.avinya.application.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.avinya.application.filter.TokenAuthorizationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final String H2_CONSOLE = "/h2-console/**";

  private static final String SWAGGER_UI = "/swagger-ui/**";

  private static final String SWAGGER_API_DOC = "/v3/api-docs/**";

  private static final String CUSTOMER_REWARD_PROGRAM_V1 = "/customer-reward-program/api/v1/**";

  private static final String AUTH_TOKEN = "/authorization/auth-token";

  private static final String REFRESH_TOKEN = "/authorization/refreshToken";

  private static final String USER_SIGNUP = "/authorization/signUp";

  private static final String PRODUCTS = "/products/**";

  @Bean
  UserDetailsService userDetailsService() {
    return new UserInfoUserDetailsService();
  }

  @Bean
  SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity, final TokenAuthorizationFilter authFilter)
    throws Exception {

    httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher(H2_CONSOLE), antMatcher(SWAGGER_UI),
      antMatcher(SWAGGER_API_DOC), antMatcher(USER_SIGNUP), antMatcher(AUTH_TOKEN), antMatcher(REFRESH_TOKEN)));

    httpSecurity.headers(headers -> headers.frameOptions()
      .disable());

    httpSecurity.authorizeHttpRequests(auth -> auth
      .requestMatchers(antMatcher(H2_CONSOLE), antMatcher(SWAGGER_UI), antMatcher(SWAGGER_API_DOC),
        antMatcher(USER_SIGNUP), antMatcher(AUTH_TOKEN), antMatcher(REFRESH_TOKEN))
      .permitAll());

    httpSecurity
      .authorizeHttpRequests(auth -> auth.requestMatchers(antMatcher(PRODUCTS), antMatcher(CUSTOMER_REWARD_PROGRAM_V1))
        .authenticated());

    httpSecurity.sessionManagement(sess -> sess.sessionCreationPolicy(STATELESS))
      .authenticationProvider(authenticationProvider())
      .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();

  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
