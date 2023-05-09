package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.ReservationDtoRequest;
import com.Travel.Travel.api.model.response.ReservationDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.IReservationService;
import com.Travel.Travel.infraestructure.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final IReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDtoResponse> post(@Valid @RequestBody ReservationDtoRequest request){
        return ResponseEntity.ok(reservationService.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationDtoResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReservationDtoResponse> put(@Valid @RequestBody ReservationDtoRequest request,@PathVariable UUID id){
        return ResponseEntity.ok(reservationService.update(request,id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping // devuelve una clave con su valor
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long idHotel){
        return ResponseEntity.ok(Collections.singletonMap("hotelPrice",reservationService.findPrice(idHotel)));
    }
}
