package com.Travel.Travel.domain.mappers;

import com.Travel.Travel.api.model.response.HotelDtoResponse;
import com.Travel.Travel.api.model.response.ReservationDtoResponse;
import com.Travel.Travel.domain.entities.jpa.ReservationEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDtoResponse reservationEntityToDtoResponse(ReservationEntity entity){

        var reservationResponse = new ReservationDtoResponse();
        var hotelResponse = new HotelDtoResponse();

        BeanUtils.copyProperties(entity,reservationResponse);
        BeanUtils.copyProperties(entity.getHotel(),hotelResponse);

        reservationResponse.setHotel(hotelResponse);
        return reservationResponse;
    }

}
