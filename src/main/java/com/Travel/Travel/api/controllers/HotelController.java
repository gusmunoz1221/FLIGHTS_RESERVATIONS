package com.Travel.Travel.api.controllers;

import com.Travel.Travel.api.model.response.HotelDtoResponse;
import com.Travel.Travel.infraestructure.abstract_services.IHotelService;
import com.Travel.Travel.infraestructure.services.HotelService;
import com.Travel.Travel.util.sortType;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final IHotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //el requestHeader es opcional
    @GetMapping
    public ResponseEntity<Page<HotelDtoResponse>> getAll(@RequestParam Integer page,
                                                         @RequestParam Integer size,
                                                         @RequestHeader(required = false) sortType sort
    ){
        if(Objects.isNull(sort)) //si sort es null asigamos el enum tipo NONE(sin ordenamiento)
            sort =  sortType.NONE;

        Page<HotelDtoResponse> hotelDtoResponse = hotelService.readAll(page,size,sort);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @GetMapping("/less_price")
    public ResponseEntity<Set<HotelDtoResponse>> getLessPrice(@RequestParam BigDecimal price){
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readLessPrice(price);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @GetMapping("/between_price")
    public ResponseEntity<Set<HotelDtoResponse>> getBetweenPrice( @RequestParam BigDecimal min, @RequestParam BigDecimal max){
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readBetweenPrices(min,max);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }

    @GetMapping("/rating")
    public ResponseEntity<Set<HotelDtoResponse>> getByRating( @RequestParam Integer rating){
        if(rating<1) //el rating es un intervalo [1,4]
            rating=1;
        if(rating>4)
            rating=4;
        Set<HotelDtoResponse> hotelDtoResponse = hotelService.readGreaterThan(rating);
        return hotelDtoResponse.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(hotelDtoResponse);
    }
}
