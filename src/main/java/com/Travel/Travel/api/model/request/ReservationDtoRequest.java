package com.Travel.Travel.api.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDtoRequest {

    //los dni tienen esta longitud dependiendo el pais
    @Size(min = 15, max = 20,message = "the size have to a length between 15 and 20 characters ")
    @NotBlank(message = "id client is mandatory")
    private String idClient;

    @Positive
    @NotNull(message = "id hotel is mandatory")
    private Long idHotel;


    @NotNull(message = "total days is mandatory")
    @Max(value = 30,message = "max 30 (thirty) days to make reservation")
    @Min(value = 1,message = "min 1 (one) days to make reservation")
    private Integer totalDays;

    @Email(message = "invalid email")
    private String email;
}
