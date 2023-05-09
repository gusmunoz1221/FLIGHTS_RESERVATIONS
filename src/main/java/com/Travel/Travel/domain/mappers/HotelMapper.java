package com.Travel.Travel.domain.mappers;

import com.Travel.Travel.api.model.response.HotelDtoResponse;
import com.Travel.Travel.domain.entities.HotelEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelDtoResponse hotelDtoResponse(HotelEntity hotelEntity) {
        HotelDtoResponse response = new HotelDtoResponse();

        BeanUtils.copyProperties(hotelEntity, response);
        return response;
    }
}
