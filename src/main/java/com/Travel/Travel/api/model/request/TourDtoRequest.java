package com.Travel.Travel.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 15, max = 20,message = "the size have to a length between 15 and 20 characters ")
    @NotBlank(message = "id customer is mandatory")
    public String customerId;
    @Size(min = 1 , message = "min flights tour per tour")
    private Set<TourFlyDtoRequest> flights;
    @Size(min = 1 , message = "min hotels tour per tour")
    private Set<TourHotelDtoRequest> hotels;
    @Email(message = "invalid email")
    private String email;
}
