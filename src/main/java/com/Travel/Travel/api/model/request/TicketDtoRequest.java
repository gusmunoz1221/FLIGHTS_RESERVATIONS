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
public class TicketDtoRequest {
    @Size(min = 15, max = 20,message = "the size have to a length between 15 and 20 characters ")
    @NotBlank(message = "id client is mandatory")
    private String idClient;
    @Positive
    @NotNull(message = "id ticket is mandatory")
    private Long idFly;
    @Email(message = "invalid email")
    private String email;
}
