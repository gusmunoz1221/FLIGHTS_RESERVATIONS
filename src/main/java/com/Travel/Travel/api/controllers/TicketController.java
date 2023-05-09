package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.request.TicketDtoRequest;
import com.Travel.Travel.api.model.response.TicketDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.ITicketService;
import com.Travel.Travel.infraestructure.services.TicketService;
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
public class TicketController {
    private final ITicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDtoResponse> post(@Valid @RequestBody @Validated TicketDtoRequest request){
        return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketDtoResponse> get(@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TicketDtoResponse> put(@Valid @RequestBody TicketDtoRequest request,@PathVariable UUID id){
        return ResponseEntity.ok(ticketService.update(request,id)) ;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping // devuelve una clave con su valor
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long idFly){
        return ResponseEntity.ok(Collections.singletonMap("flyPrice",ticketService.findPrice(idFly)));
    }
}
