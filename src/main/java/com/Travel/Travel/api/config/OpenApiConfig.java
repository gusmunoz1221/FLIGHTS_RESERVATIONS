package com.Travel.Travel.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration//se cargan inmediatamente
@OpenAPIDefinition(info = @Info(title = "Best Travel API", description = "Documentation for endPoints in Best Travel"))
public class OpenApiConfig{

}
