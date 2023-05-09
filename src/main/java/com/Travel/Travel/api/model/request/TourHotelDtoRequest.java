package com.Travel.Travel.api.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourHotelDtoRequest {
    @Positive
    @NotNull(message = "id hotel is mandatory")
    private Long id;
    private Integer totalDays;
}
