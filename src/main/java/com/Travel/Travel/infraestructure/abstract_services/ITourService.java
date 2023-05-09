package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.api.model.request.TourDtoRequest;
import com.Travel.Travel.api.model.response.TourDtoResponse;
import java.util.UUID;

public interface ITourService extends SimpleCrudService<TourDtoRequest, TourDtoResponse,Long>{
    /*-------tickets-------*/
    void removeTicket(Long tourId,UUID ticketId);
    UUID addTicket(Long tourId,Long flyId);


    /*-------resertavions------*/
    void removeReservation(Long tourId,UUID reservationId);
    UUID addReservation(Long tourId,Long reservationId,Integer totalDays);
}
