package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.TourDtoRequest;
import com.Travel.Travel.api.model.response.TourDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.ITourService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tour")
public class tourController {
    private final ITourService tourService;

    public tourController(ITourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<TourDtoResponse> post(@Valid @RequestBody TourDtoRequest request){
        return ResponseEntity.ok(tourService.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<TourDtoResponse> post(@Valid @PathVariable Long id){
        return ResponseEntity.ok(tourService.read(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        tourService.removeTicket(tourId,ticketId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String,UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        Map<String,UUID> response = Collections.singletonMap("ticketId",tourService.addTicket(tourId,flyId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId){
        tourService.removeReservation(tourId,reservationId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String,UUID>> postReservation(@PathVariable Long tourId,
                                                            @PathVariable Long hotelId,
                                                            @RequestParam Integer totalDays){
        Map<String,UUID> response = Collections.singletonMap("reservationId",tourService.addReservation(tourId,hotelId,totalDays));
        return ResponseEntity.ok(response);
    }
}
