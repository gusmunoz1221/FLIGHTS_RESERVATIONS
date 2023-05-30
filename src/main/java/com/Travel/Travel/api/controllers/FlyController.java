package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.response.FlyDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.IFlyService;
import com.Travel.Travel.util.annotation.Notify;
import com.Travel.Travel.util.sortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/fly")
@Tag(name = "Fly")
public class FlyController {
    private final IFlyService flyService;

    public FlyController(IFlyService flyService) {
        this.flyService = flyService;
    }

    @Operation(summary = "returns a flight catalog with page number and page size," +
                         "sortType is optional NONE,LOGGER AND UPPER")
    @GetMapping
    @Notify("GET Fly")
    public ResponseEntity<Page<FlyDtoResponse>> getAll( @RequestParam Integer page,
                                                        @RequestParam Integer size,
                                                        @RequestHeader(required = false) sortType sort
                                                        ){
    if(Objects.isNull(sort)) //si sort es null asigamos el enum tipo NONE(sin ordenamiento)
        sort =  sortType.NONE;

        Page<FlyDtoResponse> flyDtoResponse = flyService.readAll(page,size,sort);
        return flyDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flyDtoResponse);
    }

    @Operation(summary = "return price list less a priceParam")
    @GetMapping("/less_price")
    public ResponseEntity<Set<FlyDtoResponse>> getLessPrice( @RequestParam BigDecimal price){
        Set<FlyDtoResponse> flyDtoResponse = flyService.readLessPrice(price);
        return flyDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flyDtoResponse);
    }

    @Operation(summary = "return price list between minParam and maxParam")
    @GetMapping("/between_price")
    public ResponseEntity<Set<FlyDtoResponse>> getBetweenPrice( @RequestParam BigDecimal min, @RequestParam BigDecimal max){
        Set<FlyDtoResponse> flyDtoResponse = flyService.readBetweenPrices(min,max);
        return flyDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flyDtoResponse);
    }

    @Operation(summary = "return fly list between originParam and destinyParam")
    @GetMapping("/origin_destiny")
    public ResponseEntity<Set<FlyDtoResponse>> getByOriginDestiny(@RequestParam String origin, @RequestParam String destiny){
        Set<FlyDtoResponse> flyDtoResponse = flyService.readByOriginDestiny(origin,destiny);
        return flyDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(flyDtoResponse);
    }
}
