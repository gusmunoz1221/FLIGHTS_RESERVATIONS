package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.ReservationDtoRequest;
import com.Travel.Travel.api.model.response.ErrorsResponse;
import com.Travel.Travel.api.model.response.ReservationDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.IReservationService;
import com.Travel.Travel.infraestructure.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/reservation")
@Tag(name = "Reservation")
public class ReservationController {
    private final IReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @ApiResponse(responseCode = "400",
                 description = "when Request have a field invalid, response this",
                 content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorsResponse.class))})
    @Operation(summary = "saved a reservation in the system")
    @PostMapping
    public ResponseEntity<ReservationDtoResponse> post(@Valid @RequestBody ReservationDtoRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @Operation(summary = "return a reservation with id passed")
    @GetMapping("{id}")
    public ResponseEntity<ReservationDtoResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));
    }

    @Operation(summary = "update reservation with the id passed")
    @PutMapping("{id}")
    public ResponseEntity<ReservationDtoResponse> put(@Valid @RequestBody ReservationDtoRequest request,@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.update(request,id));
    }

    @Operation(summary = "delete reservation with the id passed")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "return a key with hotel price, with id passed")
    @GetMapping // devuelve una clave con su valor
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long idHotel,
                                                               @RequestHeader(required = false) Currency currency){
        if(Objects.isNull(currency))
            currency=Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("ticketPrice",reservationService.findPrice(idHotel,currency)));
    }
}
