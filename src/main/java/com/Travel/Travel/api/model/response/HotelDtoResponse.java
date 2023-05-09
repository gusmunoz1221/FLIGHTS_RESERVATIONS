package com.Travel.Travel.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDtoResponse {
    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;
}
