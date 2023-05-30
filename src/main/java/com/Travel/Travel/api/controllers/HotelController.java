package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.response.HotelDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.IHotelService;
import com.Travel.Travel.infraestructure.services.HotelService;
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
@RequestMapping("/hotel")
@Tag(name = "Hotel")
public class HotelController {
    private final IHotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "returns a hotels catalog with page number and page size," +
                         "sortType is optional NONE,LOGGER AND UPPER")
    @GetMapping
    @Notify("GET Hotel")
    public ResponseEntity<Page<HotelDtoResponse>> getAll(@RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestHeader(required = false) sortType sort
    ){
        if(Objects.isNull(sort)) //si sort es null asigamos el enum tipo NONE(sin ordenamiento)
            sort =  sortType.NONE;

        Page<HotelDtoResponse> hotelDtoResponse = hotelService.readAll(page,size,sort);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @Operation(summary = "return price list less a priceParam")
    @GetMapping("/less_price")
    public ResponseEntity<Set<HotelDtoResponse>> getLessPrice(@RequestParam BigDecimal price){
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readLessPrice(price);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @Operation(summary = "return price list between minParam and maxParam")
    @GetMapping("/between_price")
    public ResponseEntity<Set<HotelDtoResponse>> getBetweenPrice( @RequestParam BigDecimal min, @RequestParam BigDecimal max){
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readBetweenPrices(min,max);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @Operation(summary = "return fly list less ratingParam, with an interval between [1,5]")
    @GetMapping("/rating")
    public ResponseEntity<Set<HotelDtoResponse>> getByRating( @RequestParam Integer rating){
        if(rating<1)
            rating=1;
        if(rating>4)
            rating=5;
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readGreaterThan(rating);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }
}