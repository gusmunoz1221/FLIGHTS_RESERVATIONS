package com.Travel.Travel.infraestructure.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyDTO{
   // @JsonProperty(value = "query") //mapea la propiedad a date
    @JsonProperty(value = "quotes")
    private Map<String,BigDecimal> quote;
}
