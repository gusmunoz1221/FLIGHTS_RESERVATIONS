package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.TicketDtoRequest;
import com.Travel.Travel.api.model.response.ErrorsResponse;
import com.Travel.Travel.api.model.response.TicketDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.ITicketService;
import com.Travel.Travel.infraestructure.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
@Tag(name = "Ticket")
public class TicketController {
    private final ITicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @ApiResponse(responseCode = "400",
                description = "when Request have a field invalid, response this",
                content = {
                           @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorsResponse.class))})
    @Operation(summary = "save a ticket in the system")
    @PostMapping
    public ResponseEntity<TicketDtoResponse> post(@Valid @RequestBody @Validated TicketDtoRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @Operation(summary = "return a ticket with id passed")
    @GetMapping("{id}")
    public ResponseEntity<TicketDtoResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @Operation(summary = "update ticket with the id passed")
    @PutMapping("{id}")
    public ResponseEntity<TicketDtoResponse> put(@Valid @RequestBody TicketDtoRequest request,@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.update(request,id)) ;
    }

    @Operation(summary = "delete ticket with the passed id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "return a key with fly price, with id passed")
    @GetMapping // devuelve una clave con su valor
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long idFly){
        return ResponseEntity.ok(Collections.singletonMap("flyPrice",ticketService.findPrice(idFly)));
    }
}
