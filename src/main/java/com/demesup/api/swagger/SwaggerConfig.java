package com.demesup.api.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@SecurityScheme(
    name = "Authorization",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
    info = @Info(title = "Autopartner API", description = "Autopartner API", version = "0.0.1"),
    servers = {@Server(url="/", description = "Default Server URL")}
)
public class SwaggerConfig {

  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .security(Collections.singletonList(new SecurityRequirement().addList("Authorization")));
  }
}
