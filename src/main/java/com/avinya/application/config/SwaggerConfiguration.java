package com.avinya.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {

  @Bean
  OpenAPI openAPI() {
    return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
      .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
      .info(new Info().title("Customer Reward Program with Refresh token API")
        .description("Customer Reward Program with Refresh token API")
        .version("1.0")
        .contact(new Contact().name("Neeraj Kumar Sharma")
          .email("neerajkmsharma@gmail.com")
          .url("https://www.linkedin.com/in/neerajkmsharma"))
        .license(new License().name("License of API")
          .url("API license URL")));
  }

  private SecurityScheme createAPIKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
      .bearerFormat("JWT")
      .scheme("bearer");
  }

}
