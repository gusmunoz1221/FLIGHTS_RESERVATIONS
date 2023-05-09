package com.Travel.Travel.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TourDtoRequest {
    public String customerId;
    private Set<TourFlyDtoRequest> flights;
    private Set<TourHotelDtoRequest> hotels;
}
