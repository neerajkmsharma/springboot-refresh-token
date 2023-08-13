package com.avinya.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringbootRefreshTokenApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpringbootRefreshTokenApplication.class, args);
  }

}
