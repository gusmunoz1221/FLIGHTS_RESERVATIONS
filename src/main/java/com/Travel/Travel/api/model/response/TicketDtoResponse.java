package com.Travel.Travel.api.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDtoResponse {
    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:mm")//permite cambiar el patron de fechas
    private LocalDateTime departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:mm")
    private LocalDateTime arrivalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD") //localDate no lleva horas ni minutos
    private LocalDate purchaseDate;
    private BigDecimal price;
    private FlyDtoResponse Fly;
}
