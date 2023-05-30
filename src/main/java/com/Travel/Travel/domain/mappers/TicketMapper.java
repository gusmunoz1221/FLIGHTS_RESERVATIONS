package com.Travel.Travel.domain.mappers;

import com.Travel.Travel.api.model.response.FlyDtoResponse;
import com.Travel.Travel.api.model.response.TicketDtoResponse;
import com.Travel.Travel.domain.entities.jpa.TicketEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    //libreria beanutil hace mach con cada parametro
    public TicketDtoResponse ticketEntityToDtoResponse(TicketEntity entity){
        var ticketResponse = new TicketDtoResponse();
        var flyResponse = new FlyDtoResponse();

        BeanUtils.copyProperties(entity,ticketResponse);
        BeanUtils.copyProperties(entity.getFly(),flyResponse);

        ticketResponse.setFly(flyResponse);

        return ticketResponse;
    }
}
