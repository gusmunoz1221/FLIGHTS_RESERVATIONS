package com.Travel.Travel.infraestructure.abstract_services;

import com.Travel.Travel.api.model.request.TicketDtoRequest;
import com.Travel.Travel.api.model.response.TicketDtoResponse;
import java.math.BigDecimal;
import java.util.UUID;

public interface ITicketService extends CrudService<TicketDtoRequest, TicketDtoResponse, UUID>{

    BigDecimal findPrice(Long idFly);

}
