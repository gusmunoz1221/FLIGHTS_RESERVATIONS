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
public class TourFlyDtoRequest {
    @Positive
    @NotNull(message = "id fly is mandatory")
    private Long id;
}
