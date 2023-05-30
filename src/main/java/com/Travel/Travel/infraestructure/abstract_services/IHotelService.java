package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.api.model.response.HotelDtoResponse;

import java.util.Set;

public interface IHotelService extends CatalogService<HotelDtoResponse>{
    Set<HotelDtoResponse> readGreaterThan (Integer rating);

}
