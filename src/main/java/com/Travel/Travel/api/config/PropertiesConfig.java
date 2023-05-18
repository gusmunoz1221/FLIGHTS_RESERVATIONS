package com.Travel.Travel.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:configs/api_currency.properties")
public class PropertiesConfig {
}
