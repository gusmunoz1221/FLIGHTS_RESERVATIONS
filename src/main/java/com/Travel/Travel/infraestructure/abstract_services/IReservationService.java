package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.api.model.request.ReservationDtoRequest;
import com.Travel.Travel.api.model.response.ReservationDtoResponse;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationDtoRequest, ReservationDtoResponse, UUID>{
    BigDecimal findPrice(Long idHotel, Currency currency);

}
