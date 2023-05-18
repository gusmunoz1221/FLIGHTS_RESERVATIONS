package com.Travel.Travel.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

   @Value(value = "${api.base.url}")
    private String baseUrl;
   @Value(value = "${api.api-key}")
    private String apiKey;
   @Value(value = "${api.api-key.header}")
    private String apiKeyHeader;

   @Bean//inyeccion de dependencia --> carga una al contenedor de spring del mismo contenedor
   public WebClient currencyWebClient() {
       return WebClient
               .builder()
               .baseUrl(baseUrl)
               .defaultHeader(apiKeyHeader, apiKey)
               .build();
   }
}