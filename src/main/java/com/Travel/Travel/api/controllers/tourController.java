package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.TourDtoRequest;
import com.Travel.Travel.api.model.response.ErrorsResponse;
import com.Travel.Travel.api.model.response.TourDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tour")
@Tag(name = "Tour")
public class tourController {
    private final ITourService tourService;

    public tourController(ITourService tourService) {
        this.tourService = tourService;
    }


    @ApiResponse(responseCode = "400",
                description = "when Request have a field invalid, response this",
                content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorsResponse.class))})
    @Operation(summary = "save a tour in the system, based in list of Hotels and Flights")
    @PostMapping
    public ResponseEntity<TourDtoResponse> post(@Valid @RequestBody TourDtoRequest request){
        return ResponseEntity.ok(tourService.create(request));
    }

    @Operation(summary = "return a Tour with id passed")
    @GetMapping("{id}")
    public ResponseEntity<TourDtoResponse> post(@Valid @PathVariable Long id){
        return ResponseEntity.ok(tourService.read(id));
    }

    @Operation(summary = "delete a Tour with id passed")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "remove a ticket from tour")
    @PatchMapping("{tourId}/remove_ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId){
        tourService.removeTicket(tourId,ticketId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "add a ticket from tour")
    @PatchMapping("{tourId}/add_ticket/{flyId}")
    public ResponseEntity<Map<String,UUID>> postTicket(@PathVariable Long tourId, @PathVariable Long flyId){
        Map<String,UUID> response = Collections.singletonMap("ticketId",tourService.addTicket(tourId,flyId));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "remove a reservation from tour")
    @PatchMapping("{tourId}/remove_reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId, @PathVariable UUID reservationId){
        tourService.removeReservation(tourId,reservationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "add a ticket from tour")
    @PatchMapping("{tourId}/add_reservation/{hotelId}")
    public ResponseEntity<Map<String,UUID>> postReservation(@PathVariable Long tourId,
                                                            @PathVariable Long hotelId,
                                                            @RequestParam Integer totalDays){
        Map<String,UUID> response = Collections.singletonMap("reservationId",tourService.addReservation(tourId,hotelId,totalDays));
        return ResponseEntity.ok(response);
    }
}
