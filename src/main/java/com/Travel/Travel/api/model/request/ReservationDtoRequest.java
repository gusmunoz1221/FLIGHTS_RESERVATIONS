package com.Travel.Travel.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDtoRequest {
    private String idClient;
    private Long idHotel;
    private Integer totalDays;
}
